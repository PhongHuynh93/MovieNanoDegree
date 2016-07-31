package dhbk.android.movienanodegree.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * depends on position via Intent:
 * 0: it show the most popular movies.
 * 1: it show the highest rated movies.
 * 2: it show the most rated movies.
 */
public class ListMovieFragment extends BaseFragment implements ListMovieContract.View {
    private static final String ARG_POSITION = "position";
    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;
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
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void initView() {
        // when data again whenever system notice a refresh gesture.
        mSwiperefreshHome.setOnRefreshListener(() ->
        {
            mPresenter.fetchMoviesAsync();
        });
        // Configure the refreshing colors
        mSwiperefreshHome.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    protected void injectDependencies() {

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
    public void makePullToRefreshAppear() {
        mSwiperefreshHome.setRefreshing(true);
    }

    @Override
    public void getMoviesFromNetwork() {
        // TODO: 7/30/16 make sort in pref so the second we get to this screen, open depend on tab screen
//        callDiscoverMovies(sort, null);
    }
}
