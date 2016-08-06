package dhbk.android.movienanodegree.interactor;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.local.MoviesContract;
import dhbk.android.movienanodegree.io.MovieApiService;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by phongdth.ky on 7/15/2016
 * contain methods to interact with Movie Api
 */
public class MovieInteractor {
    public static final String MOST_POPULAR = "popularity.asc";
    public static final String HIGHEST_RATE = "vote_average.asc";
    public static final String MOST_RATE = "vote_count.asc";
    private static final String TAG = MovieInteractor.class.getSimpleName();
    private final MovieApiService mApiService;
    private final Context mContext;

    @Inject
    MovieReposition mMoviesDataSource;

    public MovieInteractor(Context context, MovieApiService apiService) {
        mContext = context;
        mApiService = apiService;
        ((MVPApp)mContext).getMovieComponent().inject(this);
    }

    // search a list of artist which equals to query
    public void performMovieSearch(String sort, @Nullable Integer page, MovieSearchServerCallback callback) {
        // TODO: 8/1/16 save data to database before show it in a list.
        mApiService.discoverMovies(sort, page)
                // run on different thread
                .subscribeOn(Schedulers.io())
                /**
                 * doOnNext: diễn tả hành động, giúp ta lấy những giá trị đang emit, vd để log hay làm gì đó (ko làm thay đồi giá trị đang emit)
                 * @see <a href="http://stackoverflow.com/questions/28402689/what-is-the-purpose-of-doonnext-in-rxjava"></a>
                 */
                // clear old data
                .doOnNext(discoverMoviesResponse -> mMoviesDataSource.clearMoviesSortTableIfNeeded(discoverMoviesResponse))
                // log new datas via log cat
                .doOnNext(discoverMoviesResponse -> mMoviesDataSource.logResponse(discoverMoviesResponse))
                /**
                 * map: transform the items emitted by an Observable by applying a function to each item
                 * @see <a href="http://reactivex.io/documentation/operators/map.html"></a>
                 * thay đổi giá trị được emit ra, 1<->1
                 */

                /** chuyển đổi json trả về từ
                 * từ {@link DiscoverMovieResponse}
                 * sang {@link DiscoverMovie}
                 */
                .map(discoverMoviesResponse -> discoverMoviesResponse.getMovies())
                /**
                 * Flatmap:
                 * @see <a href="http://reactivex.io/documentation/operators/flatmap.html"></a>
                 * tách 1 item dang phát thành nhiều items bằng hàm "from"
                 * @see <a href="http://reactivex.io/documentation/operators/from.html"></a>
                 * From: chuyển 1 lists item thành 1 observable và phát ra từng item trong list đó.
                 */
                .flatMap(movies -> Observable.from(movies))
                // save movie vừa tải về vào content provider, và return uri địa chỉ ứng với movie vừa add
                // NOTE: đã save movie vừa tải về tại đây rồi nên ko cần pass về nữa
                .map(movie -> mMoviesDataSource.saveMovie(movie))
                // chuyển uri địa chỉ của 1 movie thành 1 số id (số này là thứ tự trong 1 hàng trong db mà movies đó đang nằm đó)
                .map(movieUri -> MoviesContract.MovieEntry.getIdFromUri(movieUri))
                // save movies id to db
                .doOnNext(movieId -> mMoviesDataSource.saveMovieReference(movieId))
                // pass movies id lên UI thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        callback.onSetLoading(false);
                        callback.onDownloadAndSaveToDbSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSetLoading(false);
                        callback.onDownloadAndSaveToDbFail();
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }
                });
    }
}
