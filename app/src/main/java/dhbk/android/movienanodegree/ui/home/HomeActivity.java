package dhbk.android.movienanodegree.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import dhbk.android.movienanodegree.BaseActivity;
import dhbk.android.movienanodegree.R;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.viewpager_main_contain)
    ViewPager mViewpagerMainContain;

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
        getSupportActionBar().setTitle(R.string.home_activity_toolbar_title);
//        // Set up the navigation drawer.
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
//        setupDrawerContent(mNavView);

//        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(Constant.TAG_FRAGMENT_HOME);
//        if (homeFragment == null) {
//            // Create the fragment
//            homeFragment = HomeFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), homeFragment, R.id.contentFrame_searchartist);
//        }

        // Create the presenter
//        SearchArtistPresenter mSearchArtistPresenter = new SearchArtistPresenter(searchArtistFragment);

        // TODO: 7/28/16 change this adapter to use inject by dagger
        HomeAdapter homeAdapter = new HomeAdapter(this, getSupportFragmentManager());
        mViewpagerMainContain.setAdapter(homeAdapter);

        mTablayout.setupWithViewPager(mViewpagerMainContain);
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
