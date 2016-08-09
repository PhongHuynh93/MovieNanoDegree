package dhbk.android.movienanodegree.dagger.detailmovie;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.detailmovie.MovieVideosAdapter;

/**
 * Created by huynhducthanhphong on 8/9/16.
 * provide adapter for view {@link dhbk.android.movienanodegree.ui.detailmovie.view.MovieDetailFragment}
 */
@Module
public class MovieReviewsAdapterModule {
    @Provides
    public MovieVideosAdapter provideAdapter(Context context) {
        return new MovieVideosAdapter(context);
    }
}