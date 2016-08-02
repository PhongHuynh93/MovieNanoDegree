package dhbk.android.movienanodegree.ui.home;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieViewPagerAdapter;
import dhbk.android.movienanodegree.ui.home.component.DaggerListMovieViewComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieViewPagerAdapterModule;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * contain a fragment for Viewpager {@link ListMovieItemFragment}
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


    public ListMovieViewPagerFragment() {
        // Required empty public constructor
    }

    public static ListMovieViewPagerFragment newInstance() {
        return new ListMovieViewPagerFragment();
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
        return R.layout.fragment_item_list_movie;
    }

    @Override
    protected boolean hasToolbar() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
    protected void initView() {
        // set up viewpager
        mViewpagerFragListMovieContent.setAdapter(mListMovieViewPagerAdapter);
        mTablayoutFraglistmovie.setupWithViewPager(mViewpagerFragListMovieContent);
    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {
        checkNotNull(presenter);
        mPresenter = presenter;
    }

    /**
     * connect to server to pull the data
     */
    @Override
    public void showListOfMovies() {
        mPresenter.fetchMoviesAsync();
    }

    /**
     * show a icon indicate that the app is pulling to server
     */
    @Override
    public void makePullToRefreshAppear() {
        // call the current fraagment to make the icon
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).setThePullToRefreshAppear();
    }

    /**
     * show a icon indicate that the app is pulling to server
     */
    @Override
    public void makePullToRefreshDissappear() {
        // call the current fraagment to make the icon
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).setThePullToRefreshDissappear();
    }



    // connect to network
    @Override
    public void getMoviesFromNetwork() {
        mPresenter.callDiscoverMovies(MovieInteractor.MOST_POPULAR, null);
    }

    /**
     * show para (movies list to recycler view)
     *
     * @param movies
     * @return
     */
    @Override
    public void loadDataToLists(ArrayList<DiscoverMovie> movies) {
        // call the current fraagment to make the icon
        ((ListMovieItemFragment) mListMovieViewPagerAdapter.getRegisteredFragment(mViewpagerFragListMovieContent.getCurrentItem())).loadDataToLists(movies);
    }

    /**
     * show a snackbar to info user that cannot get the movie
     */
    @Override
    public void infoUserErrorFetchData() {
        // TODO: 8/1/16
    }
}

