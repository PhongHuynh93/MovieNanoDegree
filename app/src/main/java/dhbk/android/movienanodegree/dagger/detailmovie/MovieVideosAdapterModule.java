package dhbk.android.movienanodegree.dagger.detailmovie;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.detailmovie.MovieVideosAdapter;

/**
 * Created by huynhducthanhphong on 8/9/16.
 */
@Module
public class MovieVideosAdapterModule {
    @Provides
    public MovieVideosAdapter provideAdapter(Context context) {
        return new MovieVideosAdapter(context);
    }
}
