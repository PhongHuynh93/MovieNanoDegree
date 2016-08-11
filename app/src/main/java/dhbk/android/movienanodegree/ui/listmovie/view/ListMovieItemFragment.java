package dhbk.android.movienanodegree.ui.listmovie.view;

import android.support.annotation.NonNull;

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
}
