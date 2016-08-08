package dhbk.android.movienanodegree.dagger.listmovie;

import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieActivity;
import dhbk.android.movienanodegree.util.ActivityScoped;

import dagger.Component;

/**
 * Created by huynhducthanhphong on 7/29/16.
 */

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains presenter {@link dhbk.android.movienanodegree.ui.listmovie.ListMoviePresenter} module
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {ListMoviePresenterModule.class})
public interface ListMovieComponent {
    void inject(ListMovieActivity listMovieActivity);
}
