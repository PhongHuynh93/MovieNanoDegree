package dhbk.android.movienanodegree.ui.listmovie.view;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.DaggerListMovieComponent;
import dhbk.android.movienanodegree.dagger.listmovie.ListMoviePresenterModule;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.ui.base.BaseActivity;
import dhbk.android.movienanodegree.ui.detailmovie.view.MovieDetailActivity;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieContract;
import dhbk.android.movienanodegree.ui.listmovie.OnFragInteract;
import dhbk.android.movienanodegree.ui.listmovie.presenter.ListMoviePresenter;
import dhbk.android.movienanodegree.util.ActivityUtils;
import dhbk.android.movienanodegree.util.Constant;
import hugo.weaving.DebugLog;

public class ListMovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, OnFragInteract {

    private ListMovieViewPagerFragment mView;

    @Inject
    ListMoviePresenter mPresenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void initView() {
        // add fragment
        mView = (ListMovieViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_act_main_content);
        if (mView == null) {
            mView = ListMovieViewPagerFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mView, R.id.framelayout_act_main_content);
        }

        // TODO: 8/11/16 1 create nav
    }

    // TODO: 8/11/16 3 setup nav
    private void setupDrawerContent(NavigationView navigationView) {

    }


        @Override
    protected void injectDependencies() {
        // create the presenter
        DaggerListMovieComponent
                .builder()
                .movieComponent(((MVPApp) getApplicationContext()).getMovieComponent())
                .listMoviePresenterModule(new ListMoviePresenterModule((ListMovieContract.View) mView))
                .build()
                .inject(this);
        // set up loader to load the database
//        getLoaderManager().initLoader(Constant.LOADER_ID, null, this);
    }

    // make sure viewfragment is create
    @Override
    protected void doThingWhenResumeApp() {

    }

    /**
     * Instantiate and return a new Loader for the given ID.
     * called from method initLoader() and restartLoader()
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @DebugLog
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        /**
         * CursorLoader: A loader that queries the ContentResolver and returns a Cursor.
         * -> must provide it with content provider uri
         */
        return new CursorLoader(this, mPresenter.getContentUri(), null, null, null, null);
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @DebugLog
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // update db with cursor data
        mPresenter.updateListWithCursordata(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     * call from reset(): xóa bỏ tất cả reference để chtr có thể garbage collection
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPresenter.updateListWithCursordata(null);
    }

    /**
     * restart the loader to save movie again
     */
    @Override
    public void restartLoader() {
        getLoaderManager().restartLoader(Constant.LOADER_ID, null, this);
    }

    @Override
    protected boolean hasUseCustomeFont() {
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }

    @Override
    public void setForceLoad() {
        mView.setForceload();
    }

    @Override
    public void gotoDetailActivity(DiscoverMovieResponse.DiscoverMovie movie) {
        MovieDetailActivity.newIntent(this, movie);
    }
}
