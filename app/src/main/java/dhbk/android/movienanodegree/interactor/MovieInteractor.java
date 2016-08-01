package dhbk.android.movienanodegree.interactor;

import android.support.annotation.Nullable;

import dhbk.android.movienanodegree.io.MovieApiService;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
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
    private final MovieApiService mApiService;

    public MovieInteractor(MovieApiService apiService) {
        mApiService = apiService;
    }

    // search a list of artist which equals to query
    public void performMovieSearch(String sort, @Nullable Integer page, MovieSearchServerCallback callback) {
        // TODO: 8/1/16 save data to database before show it in a list.
        mApiService.discoverMovies(sort, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistSearchResponse -> {
                            callback.onMoviesFound(artistSearchResponse.getMovies());
                        }
                        , throwable -> {
                            callback.onFailedSearch();
                        });
    }
}
