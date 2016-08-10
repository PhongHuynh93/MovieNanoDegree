package dhbk.android.movienanodegree.io;

import android.content.Context;

import java.util.ArrayList;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.local.MoviesContract;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import dhbk.android.movienanodegree.models.MovieVideosResponse;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/8/2016.
 *  contain methods to interact with Movie Api
 */
public class MovieInteractor {
    private final MovieRetrofitEndpoint mApiService;
    private final Context mContext;
    private CompositeSubscription mCompositeSubscription;

    @Inject
    MovieReposition mMoviesDataSource;

    public MovieInteractor(Context context, MovieRetrofitEndpoint apiService) {
        checkNotNull(context);
        checkNotNull(apiService);
        mContext = context;
        mApiService = apiService;
        ((MVPApp)mContext).getMovieComponent().inject(this);

        // make Composite Subscriptions to listen the life cycler of view, so we can unsubscribe when view doesn't exist anymore
        mCompositeSubscription = new CompositeSubscription();
    }

    // search a list of movie which equals to query
    public void performMovieSearch(String sort, int page, MovieSearchServerCallback callback) {
        Subscription serchMovieSubscription = mApiService.discoverMovies(sort, page)
                // observe on different thread
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
                .map(discoverMoviesResponse -> discoverMoviesResponse.getResults())
                /**
                 * Flatmap:
                 * @see <a href="http://reactivex.io/documentation/operators/flatmap.html"></a>
                 * chuyen 1 observable thành 1 observable khác (ko 1-1 giống trong map mà có thể 1-n)
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
        mCompositeSubscription.add(serchMovieSubscription);
    }


    // : 8/10/2016 implement search video depend on movie ID
    public void performLoadVideosFromNetwork(long movieID, MovieVideosServerCallback callback) {
        //        return Observable<MovieVideosResponse>
        Subscription searchVideoSubcription = mApiService.getMovieVideos(movieID)
                .subscribeOn(Schedulers.io())
                // transform from movieVideoResponse -> array of movieResponse
                .map(movieVideosResponse -> movieVideosResponse.getResults())
                .observeOn(AndroidSchedulers.mainThread())
                // emit the array of movieResponse
                .subscribe(new Subscriber<ArrayList<MovieVideosResponse.MovieVideo>>() {
                    @Override
                    public void onCompleted() {
                        // when complete, do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSetShowOrHideVideoList();
                    }

                    @Override
                    public void onNext(ArrayList<MovieVideosResponse.MovieVideo> movieVideos) {
                        callback.onUpdateVideoAdapter(movieVideos);
                        callback.onSetShowOrHideVideoList();
                    }
                });
        mCompositeSubscription.add(searchVideoSubcription);
    }

    // : 8/10/2016 implement search reviews depend on movie ID
    public void performLoadReviewsFromNetwork(long movieID, MovieReviewsServerCallback callback) {
        Subscription searchReviewSubscription = mApiService.getMovieReviews(movieID)
                .subscribeOn(Schedulers.io())
                .map(reviewsResponse -> reviewsResponse.getResults())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<MovieReviewsResponse.MovieReview>>() {
                    @Override
                    public void onCompleted() {
                        // do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onSetShowOrHideReviewList();
                    }

                    @Override
                    public void onNext(ArrayList<MovieReviewsResponse.MovieReview> movieReviews) {
                        callback.onUpdateReviewAdapter(movieReviews);
                        callback.onSetShowOrHideReviewList();
                    }
                });
        mCompositeSubscription.add(searchReviewSubscription);
    }

    // TODO: 8/9/16 add unsubscript this
    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
        }
    }

}