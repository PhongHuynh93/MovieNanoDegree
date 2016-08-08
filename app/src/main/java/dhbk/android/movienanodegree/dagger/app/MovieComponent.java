package dhbk.android.movienanodegree.dagger.app;

import android.content.Context;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.local.MoviesLocalDataSource;
import dhbk.android.movienanodegree.io.MovieInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by phongdth.ky on 7/29/2016.
 * parent component provides context, reposition, and api server.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositionModule.class, MovieApiServiceModule.class})
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
