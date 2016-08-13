package dhbk.android.movienanodegree.ui.listmovie.view;

import android.support.annotation.NonNull;

import dhbk.android.movienanodegree.R;

/**
 * Created by phongdth.ky on 8/11/2016.
 */
public class ListMovieItemFragment extends ListMovieFragment {

    public ListMovieItemFragment() {
    }

    // : 8/11/2016 1 remove para position cause we dont need it
    @NonNull
    public static ListMovieItemFragment newInstance() {
        return new ListMovieItemFragment();
    }

    /**
     * a child fragment can use to make their own layout, set force load sepend on sort of viewpager
     */
    @Override
    protected void loadData() {
        getMListener().setForceLoad();
    }

    @Override
    public void setThePullToRefreshAppear() {
        getMSwiperefreshHome().setRefreshing(true);
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_item_list_movie;
    }
}
