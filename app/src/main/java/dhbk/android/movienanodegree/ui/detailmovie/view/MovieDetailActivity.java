package dhbk.android.movienanodegree.ui.detailmovie.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.detailmovie.DaggerDetailMovieComponent;
import dhbk.android.movienanodegree.dagger.detailmovie.DetailMoviePresenterModule;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.ui.base.BaseActivity;
import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;
import dhbk.android.movienanodegree.ui.detailmovie.presenter.MovieDetailPresenter;
import dhbk.android.movienanodegree.util.ActivityUtils;
import dhbk.android.movienanodegree.util.Constant;

public class MovieDetailActivity extends BaseActivity {
    private static final String ARG_MOVIE = "arg_movie";
    @BindView(R.id.imageview_detail_activity)
    ImageView mImageviewDetailActivity;
    @BindView(R.id.toolbar_detail_activity)
    Toolbar mToolbarDetailActivity;
    @BindView(R.id.collapsing_toolbar_layout_detail_activity)
    CollapsingToolbarLayout mCollapsingToolbarLayoutDetailActivity;
    @BindView(R.id.app_bar_layout_detail_activity)
    AppBarLayout mAppBarLayoutDetailActivity;
    @BindView(R.id.framelayout_detail_activity)
    FrameLayout mFramelayoutDetailActivity;
    @BindView(R.id.nestedScrollView_detail_activity)
    NestedScrollView mNestedScrollViewDetailActivity;
    @BindView(R.id.fab_detail_activity)
    FloatingActionButton mFabDetailActivity;
    @BindView(R.id.coordinator_layout_detail_activity)
    CoordinatorLayout mCoordinatorLayoutDetailActivity;
    private MovieDetailFragment mView;
    private DiscoverMovieResponse.DiscoverMovie mMovie;

    @Inject
    MovieDetailPresenter mPresenter;

    //  1 make intent to this class with movie parcel
    public static void newIntent(Context context, DiscoverMovieResponse.DiscoverMovie discoverMovie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(ARG_MOVIE, discoverMovie);
        context.startActivity(intent);
    }

    /**
     * use to inject presenter so, put it after initView
     */
    @Override
    protected void injectDependencies() {
        // : 8/10/16 inject presenter in here
        // create the presenter
        DaggerDetailMovieComponent
                .builder()
                .movieComponent(((MVPApp) getApplicationContext()).getMovieComponent())
                .detailMoviePresenterModule(new DetailMoviePresenterModule((DetailMovieContract.View) mView))
                .build()
                .inject(this);
    }

    @Override
    protected boolean hasUseCustomeFont() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    protected void doThingWhenResumeApp() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void initView() {
        mMovie = getMovieIntent();

        declareView();

        declareFab();

        declareToolbar();
    }

    // get movie intent from previous activity
    private DiscoverMovieResponse.DiscoverMovie getMovieIntent() {
        return getIntent().getParcelableExtra(ARG_MOVIE);
    }

    //  2 add view
    private void declareView() {
        // add fragment
        mView = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_detail_activity);
        if (mView == null) {
            mView = MovieDetailFragment.newInstance(mMovie);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mView, R.id.framelayout_detail_activity);
        }
    }

    // TODO: 8/9/2016 3 declare fab and when click, save to favorite db
    private void declareFab() {
        // get favorite from db and change image depends on this data
    }

    // : 8/9/2016 4 declare toolbar with poster image which can collapse and expand
    private void declareToolbar() {
        setSupportActionBar(mToolbarDetailActivity);
        if (getSupportActionBar() != null) {
            // set the back button to go back to previous activity
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            mToolbarDetailActivity.setNavigationOnClickListener(view -> onBackPressed());
        }
        mCollapsingToolbarLayoutDetailActivity.setTitle(mMovie.getTitle());
        mCollapsingToolbarLayoutDetailActivity.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        setTitle("");
        Glide.with(this)
                .load(Constant.POSTER_IMAGE_BASE_URL + Constant.POSTER_IMAGE_SIZE + mMovie.getBackdropPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(mImageviewDetailActivity);
    }
}
