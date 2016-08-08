package dhbk.android.movienanodegree.ui.listmovie;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.DaggerListMovieViewComponent;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieActivityModule;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieViewPagerAdapterModule;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.util.Constant;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovieViewPagerFragment extends BaseFragment implements ListMovieContract.View {
    @Inject
    ListMovieViewPagerAdapter mListMovieViewPagerAdapter;
    @BindView(R.id.viewpager_frag_list_movie_contain)
    ViewPager mViewpagerFragListMovieContent;
    @BindView(R.id.tablayout_fraglistmovie)
    TabLayout mTablayoutFraglistmovie;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private ListMovieContract.Presenter mPresenter;
    private OnFragInteract mListener;

    public ListMovieViewPagerFragment() {
        // Required empty public constructor
    }

    public static ListMovieViewPagerFragment newInstance() {
        return new ListMovieViewPagerFragment();
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

    }

    @Override
    protected void doThingWhenActivityCreated() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.home_activity_toolbar_title);
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
        return R.layout.fragment_listmovie_viewpager;
    }

    //    it has a toolbar
    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    protected void initView() {
        // set up viewpager
        mViewpagerFragListMovieContent.setAdapter(mListMovieViewPagerAdapter);
        mTablayoutFraglistmovie.setupWithViewPager(mViewpagerFragListMovieContent);
        // listen for pages change, and save the current tab of viewpager
        mViewpagerFragListMovieContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // This method will be invoked when a new page becomes selected.
            // change the content movie
            //  save this current position to refrence
            @Override
            public void onPageSelected(int position) {
                String sort;
                switch (position) {
                    case ListMovieViewPagerAdapter.MOST_POPULAR:
                        sort = Constant.MOST_POPULAR;
                        break;
                    case ListMovieViewPagerAdapter.HIGHEST_RATED:
                        sort = Constant.HIGHEST_RATED;
                        break;
                    case ListMovieViewPagerAdapter.MOST_RATED:
                        sort = Constant.MOST_RATED;
                        break;
                    default:
                        //It will never reach here, just to make compiler happy
                        throw new IllegalArgumentException("Something strange happend");
                }

                mPresenter.saveSortByPreference(sort);
                mListener.restartLoader();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * Setup the object graph and inject the dependencies needed on this fragment.
     */
    @Override
    protected void injectDependencies() {
        // Create adapter
        DaggerListMovieViewComponent
                .builder()
                .movieComponent(((MVPApp) getActivity().getApplication()).getMovieComponent())
                .listMovieActivityModule(new ListMovieActivityModule(getActivity()))
                .listMovieViewPagerAdapterModule(new ListMovieViewPagerAdapterModule())
                .build()
                .inject(this);
    }

    @Override
    public void showListOfMovies() {

    }

    @Override
    public void makePullToRefreshAppear() {

    }

    /**
     * make the icon pull to refresh dissappear
     */
    @Override
    public void makePullToRefreshDissappear() {

    }

    @Override
    public void getMoviesFromNetwork() {

    }

    /**
     * show a snackbar to info user that cannot get the movie
     */
    @Override
    public void infoUserErrorFetchData() {

    }

    @Override
    public void updateLayout() {

    }

    /**
     * stop the endless listener
     */
    @Override
    public void stopEndlessListener() {

    }

    @Override
    public void onCursorLoaded(@Nullable Cursor data) {
        // TODO: 8/8/2016 notice the page if it has not been loaded yet.
        if (!(mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem()) == null)) {
            // if we have frag, show it now
            ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).forceLoadFirstTime(false);
            ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).onCursorLoaded(data);
        }
    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
