package dhbk.android.movienanodegree.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import dhbk.android.movienanodegree.BaseFragment;
import dhbk.android.movienanodegree.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * depends on position via Intent:
 * 0: it show the most popular movies.
 * 1: it show the highest rated movies.
 * 2: it show the most rated movies.
 */
public class ListMovieFragment extends BaseFragment implements ListMovieContract.View{
    private static final String ARG_POSITION = "position";
    private ListMovieContract.Presenter mPresenter;
    // save the tab position of this view
    private String mTabLayoutPosition;

    public ListMovieFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static ListMovieFragment newInstance(int position) {
        ListMovieFragment listMovieFragment = new ListMovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        listMovieFragment.setArguments(args);
        return listMovieFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {
        checkNotNull(presenter, "Present must not null");
        mPresenter = presenter;
    }

    // get data from intent
    @Override
    protected void doThingWhenCreateApp() {
        if (getArguments() != null) {
            mTabLayoutPosition = getArguments().getString(ARG_POSITION);
        }
    }

    // start the presenter when resume
    @Override
    protected void doThingWhenResumeApp() {
        mPresenter.start();
    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void injectDependencies() {

    }

}
