package dhbk.android.movienanodegree.ui.home.module;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phongdth.ky on 7/29/2016.
 * provide a FragmentManger for {@link ListMovieViewPagerAdapterModule}
 */
@Module
public class ListMovieActivityModule {
    private final FragmentActivity mActivity;

    public ListMovieActivityModule(FragmentActivity activity) {
        mActivity  = activity;
    }


    @Provides
    FragmentManager provideFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }
}
