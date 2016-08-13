package dhbk.android.movienanodegree.ui.listmovie.view;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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
import dhbk.android.movienanodegree.ui.listmovie.EndlessRecyclerViewScrollListener;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieRecyclerViewAdapter;
import dhbk.android.movienanodegree.ui.listmovie.OnFragInteract;
import dhbk.android.movienanodegree.ui.listmovie.VerticalSpaceItemDecoration;
import dhbk.android.movienanodegree.util.HelpUtils;
import lombok.Getter;

/**
 * A simple {@link Fragment} subclass.
 * a base fragment which show a list of movie,
 * by connecting to server and load data to adapter
 *
 * 2 child frag implement this
 * {@link dhbk.android.movienanodegree.ui.listmovie.view.ListMovieItemFragment}
 *
 */
// : 8/9/2016 set the adapter in dagger
public abstract class ListMovieFragment extends BaseFragment {
    @Inject
    ListMovieRecyclerViewAdapter mListMovieRecyclerViewAdapter;

    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;

    @Getter
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;

    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

    @Getter
    private OnFragInteract mListener;


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
            // : 8/11/2016 3 make this method abtrack, so we can load depend on current view
            // : 8/12/2016 5 test khi refresh no gọi gì
            loadData();
        });

        // : 8/1/16 set adapter for recyclerview
        mListMovieRecyclerViewAdapter.setOnItemClickListener((itemView, position) -> {
            // =: 8/7/16 when click, go to another activity to show detail
            mListener.gotoDetailActivity(mListMovieRecyclerViewAdapter.getItem(position));
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
            // : 8/1/16 load more items from list
            public void onLoadMore(int page, int totalItemsCount) {

                // 1  this call refresh indicator
                setThePullToRefreshAppear();

                //  2 call service the load more movies from list and restart the loader
                mListener.setForceLoad();
            }
        };
        mRecyclerviewHomeListMovies.addOnScrollListener(endlessRecyclerViewScrollListener);
    }

    /**
     * a child fragment can use to make their own layout
     */
    protected abstract void loadData();

    @Override
    protected void doThingWhenResumeApp() {

    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }

//    @Override
//    public int getLayout() {
//        return R.layout.fragment_item_list_movie;
//    }

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

    // : 8/11/16 update the layout
    public void onCursorLoaded(@Nullable Cursor data) {
        mListMovieRecyclerViewAdapter.changeCursor(data);
        // update layout
        // if adapter have data -> show it
        // if adapter not have data -> hide the recyclerview, show a different layout to info user
        updateLayout();
    }

    public abstract void setThePullToRefreshAppear();

    public void setThePullToRefreshDissappear() {
        mSwiperefreshHome.setRefreshing(false);
    }

    // turn off loading for new news
    public void stopEndlessListener() {
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
}
