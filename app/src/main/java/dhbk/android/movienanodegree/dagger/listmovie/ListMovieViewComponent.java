package dhbk.android.movienanodegree.dagger.listmovie;

import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.ui.listmovie.view.ListMovieViewPagerFragment;
import dhbk.android.movienanodegree.util.ActivityScoped;

import dagger.Component;

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains adapter {@link ListMovieViewPagerAdapter} module
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {ListMovieActivityModule.class, ListMovieViewPagerAdapterModule.class})
public interface ListMovieViewComponent {
    void inject(ListMovieViewPagerFragment fragment);
}
