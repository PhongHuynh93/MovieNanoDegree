package dhbk.android.movienanodegree.ui.listmovie;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends Mvp.BaseView<Presenter> {


        // make the icon appear
        void makePullToRefreshAppear();

        /**
         * make the icon pull to refresh dissappear
         */
        void makePullToRefreshDissappear();

        /**
         * show a snackbar to info user that cannot get the movie
         */
        void infoUserErrorFetchData();

        void updateLayout();

        /**
         * stop the endless listener
         */
        void stopEndlessListener();

        void onCursorLoaded(@Nullable Cursor data);

        void callRestartLoader();

        void setForceload(boolean forceLoad);
    }

    interface Presenter extends Mvp.BasePresenter {

        /**
         * make the network call to get the list of movie
         * sort: is the way to get the movies from network
         */
        /**
         * make the network call to get the list of movie depend on type of sort and the integer
         *
         * @param sort
         * @param page
         */
        void callDiscoverMovies(String sort, Integer page);

        void refreshMovies();

        /**
         * determine whether the screen is fetching data or not
         */
        boolean isLoading();


        /**
         * load more movie
         */
        void loadMoreMovies();

        /**
         * save a id movies in db
         *
         * @param movieId
         */
        void saveMovieReference(long movieId);

        /**
         * save a movie in db
         *
         * @param movie
         * @return
         */
        void saveMovie(DiscoverMovieResponse.DiscoverMovie movie);

        void logResponse(DiscoverMovieResponse discoverMoviesResponse);

        void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse);

        void updateListWithCursordata(@Nullable Cursor data);

        void saveSortByPreference(String sort);

        /**
         * get the sort type which has saved from db
         */
        @NonNull
        Uri getContentUri();

        void loadTask(boolean forceUpdate, boolean firstLoad, String sort);
    }
}
