package dhbk.android.movienanodegree.data;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by phongdth.ky on 8/2/2016.
 */
@Singleton
public class MovieReposition implements MoviesDataSource {
    private final MoviesDataSource mMoviesDataSource;

    @Inject
    MovieReposition(@Local MoviesDataSource moviesDataSource) {
        mMoviesDataSource = moviesDataSource;
    }


}
