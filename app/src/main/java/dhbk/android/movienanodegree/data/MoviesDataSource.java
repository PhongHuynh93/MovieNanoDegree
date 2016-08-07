package dhbk.android.movienanodegree.data;

/**
 * Created by phongdth.ky on 8/2/2016.
 */

import android.net.Uri;
import android.support.annotation.NonNull;

import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;

/**
 * Main entry point for accessing tasks data.
 * <p>
 * For simplicity, only getTasks() and getTask() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface MoviesDataSource {

    void logResponse(DiscoverMovieResponse discoverMoviesResponse);

    Uri getSortedMoviesUri();

    void saveSortByPreference(String sort);

    interface GetCurrentPageCallback {

        void onCurrentPageLoaded(String sort, int currentpage);

        void onCurrentPageNotAvailable();
    }


    interface GetSortCallback {
        void onGetSort(String sort);
    }

//
//    interface GetUriForMovies {
//        void onGetUri(Uri uri);
//    }

    /**
     * @return the current page which has store in content provider
     */
    void getCurrentPage(@NonNull GetCurrentPageCallback callback);

    /**
     * save movie id in db
     *
     * @param movieId
     */
    void saveMovieReference(Long movieId);


    String getSort();

    /**
     * save movies in db and return the uri for movies
     *
     * @param movie
     */
    Uri saveMovie(DiscoverMovie movie);

    void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse);

    /**
     * delete movies depend on sort
     */
    void deleteMovies();
}
