package dhbk.android.movienanodegree.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.ui.base.BaseActivity;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieAdapterModule;

/**
 * contains a viewpager, which also contains a fragment {@link ListMovieFragment}:
 */
public class ListMovieActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager_main_contain)
    ViewPager mViewpagerMainContain;

    @Inject
    ListMovieAdapter mListMovieAdapter;

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
        return true;
    }

    @Override
    protected void initView() {
//        change toolbar title
        getSupportActionBar().setTitle(R.string.home_activity_toolbar_title);
        // set up viewpager
        mViewpagerMainContain.setAdapter(mListMovieAdapter);
        mTablayout.setupWithViewPager(mViewpagerMainContain);
        // TODO set up nav
    }

    @Override
    protected void injectDependencies() {
        // Create adapter
        DaggerListMovieComponent
                .builder()
                .movieComponent(((MVPApp) getApplication()).getMovieComponent())
                .listMovieActivityModule(new ListMovieActivityModule(this))
                .listMovieAdapterModule(new ListMovieAdapterModule())
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
