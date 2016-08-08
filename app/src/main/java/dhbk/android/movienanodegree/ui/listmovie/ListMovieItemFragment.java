package dhbk.android.movienanodegree.ui.listmovie;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.DaggerListMovieChildViewComponent;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieRecyclerViewAdapterModule;
import dhbk.android.movienanodegree.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovieItemFragment extends BaseFragment {
    private static final String ARG_POSITION = "position";
    private int mTabLayoutPosition; // the page in viewpager position

    @Inject
    ListMovieRecyclerViewAdapter mListMovieRecyclerViewAdapter;
    private boolean mFirstLoad = true;
    private OnFragInteract mListener;


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
        if (mFirstLoad) {
//            call restart fragment the first time
            mListener.restartLoader();
        }
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

    public void onCursorLoaded(@Nullable Cursor data) {
        mListMovieRecyclerViewAdapter.changeCursor(data);
    }

    /**
     * @param firstload
     * true: force load the content
     * false: already load, so not load again
     */
    public void forceLoadFirstTime(boolean firstload) {
        mFirstLoad = firstload;
    }
}
