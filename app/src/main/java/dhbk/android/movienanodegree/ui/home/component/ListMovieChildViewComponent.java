package dhbk.android.movienanodegree.ui.home.component;

import dagger.Component;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.ui.home.ListMovieItemFragment;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieViewPagerAdapter;
import dhbk.android.movienanodegree.ui.home.module.ListMovieRecyclerViewAdapterModule;
import dhbk.android.movienanodegree.utils.FragmentScoped;

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains adapter {@link ListMovieViewPagerAdapter} module
 */
@FragmentScoped
@Component(dependencies = MovieComponent.class, modules = {ListMovieRecyclerViewAdapterModule.class})
public interface ListMovieChildViewComponent {
    void inject(ListMovieItemFragment fragment);
}
