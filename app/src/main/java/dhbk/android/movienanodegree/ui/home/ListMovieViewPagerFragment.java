package dhbk.android.movienanodegree.ui.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieAdapter;
import dhbk.android.movienanodegree.ui.home.component.DaggerListMovieComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieAdapterModule;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * contain a fragment for Viewpager {@link ListMovieItemFragment}
 */
public class ListMovieViewPagerFragment extends BaseFragment implements ListMovieContract.View {

    @Inject
    ListMovieAdapter mListMovieAdapter;

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
        DaggerListMovieComponent
                .builder()
                .movieComponent(((MVPApp) getActivity().getApplication()).getMovieComponent())
                .listMovieActivityModule(new ListMovieActivityModule(getActivity()))
                .listMovieAdapterModule(new ListMovieAdapterModule())
                .build()
                .inject(this);
    }

    @Override
    protected void initView() {
        // set up viewpager
        mViewpagerFragListMovieContent.setAdapter(mListMovieAdapter);
        mTablayoutFraglistmovie.setupWithViewPager(mViewpagerFragListMovieContent);
    }

    @Override
    public void makePullToRefreshAppear() {

    }

    @Override
    public void getMoviesFromNetwork() {

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

