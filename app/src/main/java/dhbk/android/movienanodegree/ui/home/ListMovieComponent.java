package dhbk.android.movienanodegree.ui.home;

import dagger.Component;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieAdapterModule;
import dhbk.android.movienanodegree.ui.home.module.ListMoviePresenterModule;
import dhbk.android.movienanodegree.utils.FragmentScoped;

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains presenter {@link ListMoviePresenter} module and adapter {@link ListMovieAdapter} module
 */
@FragmentScoped
@Component(dependencies = MovieComponent.class, modules = {ListMovieActivityModule.class, ListMoviePresenterModule.class, ListMovieAdapterModule.class})
public interface ListMovieComponent {
    void inject(ListMovieActivity activity);
}
