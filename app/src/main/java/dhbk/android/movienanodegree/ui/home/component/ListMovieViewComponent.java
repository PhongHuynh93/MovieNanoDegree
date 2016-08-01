package dhbk.android.movienanodegree.ui.home.component;

import dagger.Component;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.ListMovieViewPagerFragment;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieViewPagerAdapter;
import dhbk.android.movienanodegree.ui.home.module.ListMovieActivityModule;
import dhbk.android.movienanodegree.ui.home.module.ListMovieViewPagerAdapterModule;
import dhbk.android.movienanodegree.utils.ActivityScoped;

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
