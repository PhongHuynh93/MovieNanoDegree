package dhbk.android.movienanodegree.ui.home;

import android.net.Uri;

import java.util.ArrayList;

import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;
import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends Mvp.BaseView<Presenter> {
        void showListOfMovies();

        // make the icon appear
        void makePullToRefreshAppear();

        /**
         * make the icon pull to refresh dissappear
         */
        void makePullToRefreshDissappear();

        // connect to server to pull datas
        void getMoviesFromNetwork();

        /**
         *         show para (movies list to recycler view)
         * @param list movies that pull from network
         * @return
         */
        void loadDataToLists(ArrayList<DiscoverMovie> movies);

        /**
         *         show a snackbar to info user that cannot get the movie
         */
        void infoUserErrorFetchData();

    }

    interface Presenter extends Mvp.BasePresenter {
        /**
         * refresh screen with new data
         */
        void refreshMovies();

        // fetch the movie in the network
        void fetchMoviesAsync();

        /**
         *         make the network call to get the list of movie
         * sort: is the way to get the movies from network
         */
        void callDiscoverMovies(String sort, Integer page);

        /**
         * determine whether the screen is fetching data or not
         * @return
         */
        boolean isLoading();


        /**
         * load more movie
         */
        void loadMoreMovies();

        /**
         * get the current page in the list by spare uri
         * @param uri data address that we want to query
         * @return the last page in the list
         */
        int getCurrentPage(Uri uri);

        void saveMovieReference(Long movieId);

        Uri saveMovie(DiscoverMovie movie);

        void logResponse(DiscoverMovieResponse discoverMoviesResponse);

        void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse);
    }

}
