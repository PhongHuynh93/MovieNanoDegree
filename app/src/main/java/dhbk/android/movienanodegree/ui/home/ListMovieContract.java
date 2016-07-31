package dhbk.android.movienanodegree.ui.home;

import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends Mvp.BaseView<Presenter> {
        void showListOfMovies();

        // make the icon appear
        void makePullToRefreshAppear();

        // connect to server to pull datas
        void getMoviesFromNetwork();


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
