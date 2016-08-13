package dhbk.android.movienanodegree.dagger.listmovie;

import dagger.Component;
import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.ui.listmovie.view.ListMovieFragment;
import dhbk.android.movienanodegree.util.ActivityScoped;

/**
 * Created by phongdth.ky on 7/29/2016.
 * depend on {@link MovieComponent}
 * contains adapter {@link ListMovieRecyclerViewAdapterModule} module
 */
@ActivityScoped
@Component(dependencies = MovieComponent.class, modules = {ListMovieRecyclerViewAdapterModule.class})
public interface ListMovieChildViewComponent {
    void inject(ListMovieFragment fragment);
}
