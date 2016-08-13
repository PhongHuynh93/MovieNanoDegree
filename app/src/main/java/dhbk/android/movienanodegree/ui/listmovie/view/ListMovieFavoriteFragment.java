package dhbk.android.movienanodegree.ui.listmovie.view;

import android.database.Cursor;
import android.support.annotation.Nullable;

import dhbk.android.movienanodegree.R;
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
     *  1 a child fragment can use to make their own layout
     */
    @Override
    protected void loadData() {
        getMListener().restartLoader();
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

    /**
     * when the load complete, update the layout by call the parent method
     * stop refreshing indicate
     * @param data
     */
    @Override
    public void onCursorLoaded(@Nullable Cursor data) {
        super.onCursorLoaded(data);
        setThePullToRefreshDissappear();
    }

    @Override
    public void setThePullToRefreshAppear() {

    }

    @Override
    protected String getTitle() {
        return "Favorite";
    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_item_list__favorite_movie;
    }
}
