package dhbk.android.movienanodegree.ui.home;


import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CursorAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.home.adapter.EndlessRecyclerViewScrollListener;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieRecyclerViewAdapter;
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
public class ListMovieItemFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String ARG_POSITION = "position";
    private static final String TAG = ListMovieItemFragment.class.getSimpleName();
    private static final int LOADER_ID = 0;
    @Inject
    ListMovieRecyclerViewAdapter mListMovieRecyclerViewAdapter;

    @BindView(R.id.recyclerview_home_list_movies)
    RecyclerView mRecyclerviewHomeListMovies;
    @BindView(R.id.swiperefresh_home)
    SwipeRefreshLayout mSwiperefreshHome;
    // save the tab position of this view
    private int mTabLayoutPosition;
    private EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;

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

        // init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /**
         * CursorLoader: A loader that queries the ContentResolver and returns a Cursor.
         * -> must provide it with content provider uri
         */
        return new CursorLoader(getActivity(),
                mListMovieRecyclerViewAdapter.getContentUri(),
                null,
                null,
                null,
                null
        );
    }

    public Uri getContentUri() {
        return mContentUri;
    }

    /**
     * Called when a previously created loader has finished its load.
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

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


    public void loadDataToLists(ArrayList<DiscoverMovie> movies) {
        mListMovieRecyclerViewAdapter.replaceAnotherData(movies);
    }


    public void stopEndlessListener() {
        // turn off loading for new news
        endlessRecyclerViewScrollListener.setLoading(false);
    }


}
