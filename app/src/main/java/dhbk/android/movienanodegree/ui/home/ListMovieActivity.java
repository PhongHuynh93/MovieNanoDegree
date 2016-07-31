package dhbk.android.movienanodegree.ui.home;

import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.base.BaseActivity;
import dhbk.android.movienanodegree.ui.home.component.DaggerListMoviePresenterComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMoviePresenterModule;
import dhbk.android.movienanodegree.utils.ActivityUtils;

/**
 * contains a viewpager, which also contains a fragment {@link ListMovieItemFragment}:
 */
public class ListMovieActivity extends BaseActivity {
    @BindView(R.id.framelayout_act_main_content)
    FrameLayout mFramelayoutActMainContent;
    private ListMovieViewPagerFragment mListMovieViewPagerFragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
