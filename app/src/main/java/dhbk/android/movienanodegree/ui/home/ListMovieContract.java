package dhbk.android.movienanodegree.ui.home;

import java.util.ArrayList;

import dhbk.android.movienanodegree.io.model.DiscoverMovie;
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
        // fetch the movie in the network
        void fetchMoviesAsync();

        /**
         *         make the network call to get the list of movie
         * sort: is the way to get the movies from network
         */
        void callDiscoverMovies(String sort, Integer page);
    }

}
