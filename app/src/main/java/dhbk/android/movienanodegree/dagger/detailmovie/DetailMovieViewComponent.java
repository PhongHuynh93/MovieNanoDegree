package dhbk.android.movienanodegree.dagger.detailmovie;

import dagger.Component;
import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.ui.detailmovie.view.MovieDetailFragment;
import dhbk.android.movienanodegree.util.ActivityScoped;

/**
 * Created by huynhducthanhphong on 8/9/16.
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {MovieVideosAdapterModule.class, MovieReviewsAdapterModule.class})
public interface DetailMovieViewComponent {
    void inject(MovieDetailFragment fragment);
}
