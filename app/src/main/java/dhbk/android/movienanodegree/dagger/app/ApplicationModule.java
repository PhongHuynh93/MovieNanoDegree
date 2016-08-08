package dhbk.android.movienanodegree.dagger.app;

/**
 * Created by phongdth.ky on 8/8/2016.
 */

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * module provides context
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
