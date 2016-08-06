package dhbk.android.movienanodegree.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;
import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/2/2016.
 * control local {@link dhbk.android.movienanodegree.data.local.MoviesLocalDataSource}and remote reposition
 */
@Singleton
public class MovieReposition implements MoviesDataSource {
    private final MoviesDataSource mMoviesLocalDataSource;
    private final Context mContext;

    @Inject
    MovieReposition(@Local MoviesDataSource moviesDataSource, Context context) {
        mMoviesLocalDataSource = moviesDataSource;
        mContext = context;
    }

    /**
     * @param callback
     * @return the current page which has store in content provider
     */
    @Override
    public void getCurrentPage(@NonNull GetCurrentPageCallback callback) {
        checkNotNull(callback);

        // check from local db
        mMoviesLocalDataSource.getCurrentPage(new GetCurrentPageCallback() {
            @Override
            public void onCurrentPageLoaded(String sort, int current) {
                callback.onCurrentPageLoaded(sort, current);
            }

            @Override
            public void onCurrentPageNotAvailable() {

            }
        });

        // check from network db
    }

    /**
     * save movie id in db
     *
     * @param movieId
     */
    @Override
    public void saveMovieReference(Long movieId) {
        checkNotNull(movieId);
        // save movieid in local and remote db
        mMoviesLocalDataSource.saveMovieReference(movieId);
    }


    @Override
    public Observable<String> getSort() {
        return mMoviesLocalDataSource.getSort();
    }



    @Override
    public Uri saveMovie(DiscoverMovie movie) {
        // save db in local
        return mMoviesLocalDataSource.saveMovie(movie);
    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
        mMoviesLocalDataSource.logResponse(discoverMoviesResponse);
    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        mMoviesLocalDataSource.clearMoviesSortTableIfNeeded(discoverMoviesResponse);
    }

    @Override
    public void deleteMovies() {
        // delete in db
        mMoviesLocalDataSource.deleteMovies();
    }

//    public Observable<Uri> getSortedMoviesUri() {
//        // get the sort string
//        getSort().subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String sort) {
//                switch (sort) {
//                    case MovieInteractor.MOST_POPULAR:
//
//                        return Observable.just(MoviesContract.MostPopularMovies.CONTENT_URI);
//                    case MovieInteractor.HIGHEST_RATED:
//                        return MoviesContract.HighestRatedMovies.CONTENT_URI;
//                    case MovieInteractor.MOST_RATED:
//                        return MoviesContract.MostRatedMovies.CONTENT_URI;
//                    default:
//                        throw new IllegalStateException("Unknown sort.");
//                }
//            }
//        });
//
//    }

}
