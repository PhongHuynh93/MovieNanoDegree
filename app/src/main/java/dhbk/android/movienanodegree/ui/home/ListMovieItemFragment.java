package dhbk.android.movienanodegree.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.base.BaseFragment;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * depends on position via Intent:
 * 0: it show the most popular movies.
 * 1: it show the highest rated movies.
 * 2: it show the most rated movies.
 */
public class ListMovieItemFragment extends BaseFragment {
    private static final String ARG_POSITION = "position";
    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;
    // save the tab position of this view
    private int mTabLayoutPosition;

    public ListMovieItemFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static ListMovieItemFragment newInstance(int position) {
        ListMovieItemFragment listMovieItemFragment = new ListMovieItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        listMovieItemFragment.setArguments(args);
        return listMovieItemFragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_listmovie_viewpager;
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

    // get data from intent
    @Override
    protected void doThingWhenCreateApp() {
        if (getArguments() != null) {
            mTabLayoutPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    protected void doThingWhenActivityCreated() {
        // when data again whenever system notice a refresh gesture.
        mSwiperefreshHome.setOnRefreshListener(() ->
        {
            Fragment parentFrag = getActivity().getSupportFragmentManager().findFragmentById(R.id.framelayout_act_main_content);
            if (parentFrag instanceof ListMovieViewPagerFragment){
                ((ListMovieViewPagerFragment)parentFrag).showListOfMovies();

            }
        });
        // Configure the refreshing colors
        mSwiperefreshHome.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // TODO: 8/1/16 set adapter for recyclerview
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerviewHomeListMovies.setLayoutManager(linearLayoutManager);
//        mRecyclerviewHomeListMovies.addOnScrollListener();

    }

    @Override
    protected void doThingWhenResumeApp() {
    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }

    public void setThePullToRefreshAppear() {
        mSwiperefreshHome.setRefreshing(true);
    }

    public void setThePullToRefreshDissappear() {
        mSwiperefreshHome.setRefreshing(false);
    }
}
