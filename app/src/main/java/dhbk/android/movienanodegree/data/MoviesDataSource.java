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

    //    get the current page of movies in local db
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

    /**
     * get into the db and get the state of fab
     *
     * @return
     * @param movie
     */
    boolean isFavorite(DiscoverMovieResponse.DiscoverMovie movie);

    /**
     * remove favorite from db by compare movie ID
     * @param movie
     */
    void removeFavorite(DiscoverMovieResponse.DiscoverMovie movie);

    /**
     * add favorite to db with movie ID
     * @param movie
     */
    void addFavorite(DiscoverMovieResponse.DiscoverMovie movie);

    /**
     * get the uri of the favorite resource
     * @return
     */
    Uri getFavMovieUri();
}

