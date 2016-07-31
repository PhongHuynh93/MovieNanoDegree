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
    private final MovieApiService mApiService;

    public MovieInteractor(MovieApiService apiService) {
        mApiService = apiService;
    }

    // search a list of artist which equals to query
    public void performMovieSearch(String sort, @Nullable Integer page, MovieSearchServerCallback callback) {
        mApiService.discoverMovies(sort, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistSearchResponse -> {
                            callback.onMoviesFound(artistSearchResponse.getMovies());
                        }
                        , throwable -> {
                            callback.onFailedSearch();
                        });
    }
}
