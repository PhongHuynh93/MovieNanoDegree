package dhbk.android.movienanodegree.data;

/**
 * Created by phongdth.ky on 8/8/2016.
 */

import android.net.Uri;
import android.support.annotation.NonNull;

import dhbk.android.movienanodegree.models.DiscoverMovieResponse;

/**
 * Main entry point for accessing tasks data.
 */
public interface MoviesDataSource {

    void logResponse(@NonNull DiscoverMovieResponse discoverMoviesResponse);

    @NonNull
    Uri getSortedMoviesUri();

    void saveSortByPreference(@NonNull String sort);

    int getCurrentPage();

     //save movie id in db
    void saveMovieReference(@NonNull Long movieId);

    @NonNull
    String getSort();

     // save movies in db and return the uri for movies
    Uri saveMovie(@NonNull DiscoverMovieResponse.DiscoverMovie movie);

    void clearMoviesSortTableIfNeeded(@NonNull DiscoverMovieResponse discoverMoviesResponse);

     // delete movies depend on sort
    void deleteMovies();
}

