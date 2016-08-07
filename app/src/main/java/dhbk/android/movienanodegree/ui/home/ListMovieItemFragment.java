package dhbk.android.movienanodegree.ui.home;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.home.adapter.EndlessRecyclerViewScrollListener;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieRecyclerViewAdapter;
import dhbk.android.movienanodegree.ui.home.adapter.OnItemClickListener;
import dhbk.android.movienanodegree.ui.home.adapter.VerticalSpaceItemDecoration;
import dhbk.android.movienanodegree.ui.home.component.DaggerListMovieChildViewComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMovieRecyclerViewAdapterModule;
import dhbk.android.movienanodegree.utils.HelpUtils;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * depends on position via Intent:
 * 0: it show the most popular movies.
 * 1: it show the highest rated movies.
 * 2: it show the most rated movies.
 */
public class ListMovieItemFragment extends BaseFragment {
    private static final String ARG_POSITION = "position";
    private static final String TAG = ListMovieItemFragment.class.getSimpleName();
    @Inject
    ListMovieRecyclerViewAdapter mListMovieRecyclerViewAdapter;

    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
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
        // Create adapter
        DaggerListMovieChildViewComponent
                .builder()
                .movieComponent(((MVPApp) getActivity().getApplication()).getMovieComponent())
                .listMovieRecyclerViewAdapterModule(new ListMovieRecyclerViewAdapterModule())
                .build()
                .inject(this);
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

        // : 8/1/16 set adapter for recyclerview
        mListMovieRecyclerViewAdapter.setOnItemClickListener((itemView, position) -> onItemSelectedListener.onItemSelected(adapter.getItem(position)));
        mRecyclerviewHomeListMovies.setAdapter(mListMovieRecyclerViewAdapter);
        // make list show 1 vertical column of data
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerviewHomeListMovies.setLayoutManager(linearLayoutManager);
        // add space between list
        mRecyclerviewHomeListMovies.addItemDecoration(new VerticalSpaceItemDecoration((int)HelpUtils.getPixelForDp(getContext(), 13)));
        mRecyclerviewHomeListMovies.setHasFixedSize(true);
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // TODO: 8/1/16 implement this function
            }
        };
        mRecyclerviewHomeListMovies.addOnScrollListener(endlessRecyclerViewScrollListener);


    }

    @Override
    protected void doThingWhenResumeApp() {
        Log.i(TAG, "doThingWhenResumeApp: ");
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

    public void stopEndlessListener() {
        // turn off loading for new news
        endlessRecyclerViewScrollListener.setLoading(false);
    }

    /**
     * update layout depend on adapter items
     */
    public void updateLayout() {
        if (mListMovieRecyclerViewAdapter.getItemCount() == 0) {
            mRecyclerviewHomeListMovies.setVisibility(View.GONE);
//            noMoviesView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerviewHomeListMovies.setVisibility(View.VISIBLE);
//            noMoviesView.setVisibility(View.GONE);
        }
    }

    public void onCursorLoaded(Cursor data) {
        mListMovieRecyclerViewAdapter.changeCursor(data);
    }

}
