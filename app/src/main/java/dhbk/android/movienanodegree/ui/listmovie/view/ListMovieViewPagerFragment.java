package dhbk.android.movienanodegree.ui.listmovie.view;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.DaggerListMovieViewComponent;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieActivityModule;
import dhbk.android.movienanodegree.dagger.listmovie.ListMovieViewPagerAdapterModule;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieContract;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieViewPagerAdapter;
import dhbk.android.movienanodegree.ui.listmovie.OnFragInteract;
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
        // every change in page, restart the loader to load datas from local data again.
        mListener.restartLoader();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // : 8/11/2016 1 call nav to open
                mListener.openNavDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            //  save this current position to refrence.
            // when page select, force load in the first time
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
                // save the current sort
                mPresenter.saveSortByPreference(sort);
//                not call this method
//                mPresenter.loadTask(false, true, sort);
                //  3 every change in page, restart the loader to load datas from local data again,
                //  call this method to load db from db every time the page select change
                mListener.restartLoader();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * save the current tab of the viewpager and force load datas from network
     */
    @Override
    public void setForceload() {
        // change the sort type
        String sort;
        switch (mViewpagerFragListMovieContent.getCurrentItem()) {
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
        // call when a pull to refresh, so alway force load -> set para 1st to true
        mPresenter.loadTask(true, false, sort);
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
    public void makePullToRefreshAppear() {
// call the current fraagment to make the icon
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).setThePullToRefreshAppear();
    }

    /**
     * make the icon pull to refresh dissappear
     */
    @Override
    public void makePullToRefreshDissappear() {
        // call the current fraagment to make the icon
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).setThePullToRefreshDissappear();
    }


    @Override
    public void updateLayout() {
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).updateLayout();
    }

    /**
     * stop the endless listener
     */
    @Override
    public void stopEndlessListener() {
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).stopEndlessListener();
    }

    @Override
    public void onCursorLoaded(@Nullable Cursor data) {
        if ((mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem()) != null)) {
            // if we have frag, show it now
            ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).onCursorLoaded(data);
        }
    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }


    /**
     * restart loader to load datas from local database again
     */
    @Override
    public void callRestartLoader() {
        mListener.restartLoader();
    }
}
