package dhbk.android.movienanodegree.dagger.detailmovie;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.detailmovie.MovieReviewsAdapter;
import dhbk.android.movienanodegree.util.ActivityScoped;

/**
 * Created by huynhducthanhphong on 8/9/16.
 * provide adapter for view {@link dhbk.android.movienanodegree.ui.detailmovie.view.MovieDetailFragment}
 */
@Module
public class MovieReviewsAdapterModule {
    @Provides
    @ActivityScoped
    public MovieReviewsAdapter provideAdapter(Context context) {
        return new MovieReviewsAdapter(context);
    }
}
