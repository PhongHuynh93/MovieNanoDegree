package dhbk.android.movienanodegree.ui.home.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieViewPagerAdapter;

/**
 * Created by phongdth.ky on 7/29/2016.
 * provide a adapter for viewpager in {@link dhbk.android.movienanodegree.ui.home.ListMovieActivity}
 */

@Module
public class ListMovieViewPagerAdapterModule {

    /**
     *     context from {@link MovieComponent}
     *     fm from {@link ListMovieActivityModule}
     */
    @Provides
    public ListMovieViewPagerAdapter provideAdapter(Context context, FragmentManager fm) {
        return new ListMovieViewPagerAdapter(context, fm);
    }
}
