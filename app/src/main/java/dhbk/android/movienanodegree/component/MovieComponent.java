package dhbk.android.movienanodegree.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.MovieRepositionModule;
import dhbk.android.movienanodegree.data.local.MoviesLocalDataSource;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.module.ApplicationModule;
import dhbk.android.movienanodegree.module.MovieModule;

/**
 * Created by phongdth.ky on 7/29/2016.
 * parent component
 * type: Component Dependencies
 */
@Singleton
@Component(modules = {ApplicationModule.class, MovieRepositionModule.class, MovieModule.class})
public interface MovieComponent {
    Context getContext();
    MovieInteractor getMovieInteractor();
    MovieReposition getMovieReposition();

    /**
     * use shred preference in this class
     * @param moviesLocalDataSource
     */
    void inject(MoviesLocalDataSource moviesLocalDataSource);

    /**
     * use {@link MoviesLocalDataSource}
     */
    void inject(MovieInteractor movieInteractor);
}
