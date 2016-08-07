package dhbk.android.movienanodegree.ui.home;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.base.BaseActivity;
import dhbk.android.movienanodegree.ui.home.component.DaggerListMoviePresenterComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMoviePresenterModule;
import dhbk.android.movienanodegree.utils.ActivityUtils;

/**
 * contains a viewpager, which also contains a fragment {@link ListMovieItemFragment}:
 */
public class ListMovieActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;

    @BindView(R.id.framelayout_act_main_content)
    FrameLayout mFramelayoutActMainContent;
    private ListMovieViewPagerFragment mListMovieViewPagerFragment;
    @Inject
    ListMoviePresenter mListMoviePresenter;

    //    @Inject
//    ListMoviePresenter mListMoviePresenter;
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
    protected void initView() {
        // add fragment
        mListMovieViewPagerFragment = (ListMovieViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_act_main_content);
        if (mListMovieViewPagerFragment == null) {
            // Create the fragment
            mListMovieViewPagerFragment = ListMovieViewPagerFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mListMovieViewPagerFragment, R.id.framelayout_act_main_content);
        }
        // TODO set up nav

        // set up loader
        // init loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    // call after initVIew()
    @Override
    protected void injectDependencies() {
        // create the presenter
        DaggerListMoviePresenterComponent
                .builder()
                .movieComponent(((MVPApp) getApplicationContext()).getMovieComponent())
                .listMoviePresenterModule(new ListMoviePresenterModule((ListMovieContract.View) mListMovieViewPagerFragment))
                .build()
                .inject(this);
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
        return new CursorLoader(this,
                mListMoviePresenter.getContentUri(),
                null,
                null,
                null,
                null
        );
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // update db with cursor data
        mListMoviePresenter.updateListWithCursordata(data);
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
        adapter.changeCursor(null);
        updateGridLayout();
    }

}
