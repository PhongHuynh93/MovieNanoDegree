package dhbk.android.movienanodegree.dagger.listmovie;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import dhbk.android.movienanodegree.ui.listmovie.ListMovieActivity;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieViewPagerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phongdth.ky on 7/29/2016.
 * provide a adapter for viewpager in {@link ListMovieActivity}
 */

@Module
public class ListMovieViewPagerAdapterModule {
    @Provides
    public ListMovieViewPagerAdapter provideAdapter(Context context, FragmentManager fm) {
        return new ListMovieViewPagerAdapter(context, fm);
    }
}
