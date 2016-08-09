package dhbk.android.movienanodegree.ui.listmovie;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.DaggerListMovieChildViewComponent;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieRecyclerViewAdapterModule;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.util.HelpUtils;
import hugo.weaving.DebugLog;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovieItemFragment extends BaseFragment {
    private static final String ARG_POSITION = "position";
    @Inject
    ListMovieRecyclerViewAdapter mListMovieRecyclerViewAdapter;

    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    private int mTabLayoutPosition;
    private OnItemSelectedListener onItemSelectedListener;
    private OnFragInteract mListener;
    private boolean mFirstload = true;


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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mListener = (OnFragInteract) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    protected void doThingWhenCreateApp() {
        if (getArguments() != null) {
            mTabLayoutPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    protected void doThingWhenActivityCreated() {
        // Configure the refreshing colors
        mSwiperefreshHome.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwiperefreshHome.setOnRefreshListener(() ->{
            mListener.setForceLoad(true);
        });

        // : 8/1/16 set adapter for recyclerview
        mListMovieRecyclerViewAdapter.setOnItemClickListener((itemView, position) -> {
            // TODO: 8/7/16 when click, go to another activity to show detail
        });
        mRecyclerviewHomeListMovies.setAdapter(mListMovieRecyclerViewAdapter);
        // make list show 1 vertical column of data
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerviewHomeListMovies.setLayoutManager(linearLayoutManager);
        // add space between list
        mRecyclerviewHomeListMovies.addItemDecoration(new VerticalSpaceItemDecoration((int) HelpUtils.getPixelForDp(getContext(), 13)));
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

    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_item_list_movie;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void initView() {

    }

    /**
     * Setup the object graph and inject the dependencies needed on this fragment.
     */
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

    @DebugLog
    public void onCursorLoaded(@Nullable Cursor data) {
        mListMovieRecyclerViewAdapter.changeCursor(data);
        // update layout
        // if adapter have data -> show it
        // if adapter not have data -> hide the recyclerview, show a different layout to info user
        updateLayout();
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

    public void updateLayout() {
        if (mListMovieRecyclerViewAdapter.getItemCount() == 0) {
            mRecyclerviewHomeListMovies.setVisibility(View.GONE);
//            noMoviesView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerviewHomeListMovies.setVisibility(View.VISIBLE);
//            noMoviesView.setVisibility(View.GONE);
        }
    }

    public boolean getFirstload() {
        return mFirstload;
    }

    public void setFirstload(boolean firstload) {
        mFirstload = firstload;
    }
}
