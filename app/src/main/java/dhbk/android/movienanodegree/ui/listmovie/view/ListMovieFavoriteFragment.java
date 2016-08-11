package dhbk.android.movienanodegree.ui.listmovie.view;

import dhbk.android.movienanodegree.ui.listmovie.ListMovieContract;

/**
 * Created by phongdth.ky on 8/11/2016.
 */
public class ListMovieFavoriteFragment extends  ListMovieFragment implements ListMovieContract.View{

    public ListMovieFavoriteFragment() {
    }

    public static ListMovieFavoriteFragment newInstance() {
        return new ListMovieFavoriteFragment();
    }

    /**
     * a child fragment can use to make their own layout
     */
    @Override
    protected void loadData() {

    }


//    not use this method
    @Override
    public void makePullToRefreshAppear() {

    }

    @Override
    public void makePullToRefreshDissappear() {

    }

    @Override
    public void callRestartLoader() {

    }

    @Override
    public void setForceload() {

    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {

    }
}
