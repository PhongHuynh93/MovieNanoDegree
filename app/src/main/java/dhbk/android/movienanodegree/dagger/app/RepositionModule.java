package dhbk.android.movienanodegree.dagger.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dhbk.android.movienanodegree.data.MoviesDataSource;
import dhbk.android.movienanodegree.data.local.MoviesLocalDataSource;
import dhbk.android.movienanodegree.util.Local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by phongdth.ky on 8/8/2016.
 * contains local, remote, cache, shred preferences reposition
 */
@Module
public class RepositionModule {
    @Provides
    @Singleton
    SharedPreferences getSharePreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Singleton
    @Provides
    @Local
    MoviesDataSource provideMoviesLocalDataSource(Context context) {
        return new MoviesLocalDataSource(context);
    }
}
