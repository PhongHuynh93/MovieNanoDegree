package dhbk.android.movienanodegree.ui.home;

import dagger.Component;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMoviePresenterModule;
import dhbk.android.movienanodegree.utils.FragmentScoped;

/**
 * Created by huynhducthanhphong on 7/29/16.
 */
/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains presenter {@link ListMoviePresenter} module
 */
@FragmentScoped
@Component(dependencies = MovieComponent.class, modules = {ListMoviePresenterModule.class})
public interface ListMoviePresenterComponent {
    void inject(ListMovieActivity adapter);
}
