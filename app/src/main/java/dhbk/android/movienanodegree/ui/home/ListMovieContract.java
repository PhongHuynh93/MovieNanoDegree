package dhbk.android.movienanodegree.ui.home;

import dhbk.android.movienanodegree.BasePresenter;
import dhbk.android.movienanodegree.BaseView;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends BaseView<Presenter> {
        // make the icon appear
        void makePullToRefreshAppear();

        // connect to server to pull datas
        void getMoviesFromNetwork();
    }

    interface Presenter extends BasePresenter {
        // fetch the movie in the network
        void fetchMoviesAsync();
    }

}
