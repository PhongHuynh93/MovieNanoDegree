package dhbk.android.movienanodegree.ui.home;

import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends Mvp.BaseView<Presenter> {
        // make the icon appear
        void makePullToRefreshAppear();

        // connect to server to pull datas
        void getMoviesFromNetwork();
    }

    interface Presenter extends Mvp.BasePresenter {
        // fetch the movie in the network
        void fetchMoviesAsync();
    }

}
