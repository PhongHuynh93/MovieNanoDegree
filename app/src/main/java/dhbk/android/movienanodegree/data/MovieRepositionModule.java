package dhbk.android.movienanodegree.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.data.local.MoviesLocalDataSource;

/**
 * Created by phongdth.ky on 8/2/2016.
 */
@Module
public class MovieRepositionModule {
    /**
     * share preference save key-value data
     * @param context
     * @return
     */
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

    // TODO: 8/2/2016 add network data source
}
