package dhbk.android.movienanodegree.ui.home;

import dagger.Component;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieAdapterModule;
import dhbk.android.movienanodegree.utils.ActivityScoped;

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains adapter {@link ListMovieAdapter} module
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {ListMovieActivityModule.class, ListMovieAdapterModule.class})
public interface ListMovieComponent {
    void inject(ListMovieViewPagerFragment fragment);
}
