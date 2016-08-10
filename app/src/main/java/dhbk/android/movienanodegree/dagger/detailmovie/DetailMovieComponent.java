package dhbk.android.movienanodegree.dagger.detailmovie;

import dagger.Component;
import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.ui.detailmovie.view.MovieDetailActivity;
import dhbk.android.movienanodegree.util.ActivityScoped;

/**
 * Created by phongdth.ky on 8/10/2016.
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {DetailMoviePresenterModule.class})
public interface DetailMovieComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
