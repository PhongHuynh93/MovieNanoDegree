package dhbk.android.movienanodegree.ui.home;

import android.database.Cursor;

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
         *         show a snackbar to info user that cannot get the movie
         */
        void infoUserErrorFetchData();

        void updateLayout();

        /**
         * stop the endless listener
         */
        void stopEndlessListener();

        void onCursorLoaded(Cursor data);
    }

    interface Presenter extends Mvp.BasePresenter {
        // fetch the movie in the network
        void fetchMoviesAsync();

        /**
         *         make the network call to get the list of movie
         * sort: is the way to get the movies from network
         */
        void callDiscoverMovies(String sort, Integer page);

        void refreshMovies();

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
         * save a id movies in db
         * @param movieId
         */
        void saveMovieReference(Long movieId);

        /**
         * save a movie in db
         * @param movie
         * @return
         */
        void saveMovie(DiscoverMovie movie);

        void logResponse(DiscoverMovieResponse discoverMoviesResponse);

        void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse);

        void updateListWithCursordata(Cursor data);


        void saveSortByPreference(String sort);
    }

}
