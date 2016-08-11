package dhbk.android.movienanodegree.data;

import android.content.Context;
import android.net.Uri;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.local.MoviesLocalDataSource;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.util.Constant;
import dhbk.android.movienanodegree.util.Local;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/8/2016.
 * control local {@link MoviesLocalDataSource} and remote reposition
 */
public class MovieReposition implements MoviesDataSource{
    private final MoviesDataSource mMoviesLocalDataSource;
    private final Context mContext;

    @Inject
    MovieReposition(@Local MoviesDataSource moviesDataSource, Context context) {
        mMoviesLocalDataSource = moviesDataSource;
        mContext = context;
    }

    @Override
    public int getCurrentPage() {
        return mMoviesLocalDataSource.getCurrentPage();
    }

    @Override
    public void saveMovieReference(Long movieId) {
        checkNotNull(movieId);
        mMoviesLocalDataSource.saveMovieReference(movieId);
    }


    @Override
    public String getSort() {
        return mMoviesLocalDataSource.getSort();
    }

    @Override
    public Uri saveMovie(DiscoverMovieResponse.DiscoverMovie movie) {
        checkNotNull(movie);
        return mMoviesLocalDataSource.saveMovie(movie);
    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
        checkNotNull(discoverMoviesResponse);
        mMoviesLocalDataSource.logResponse(discoverMoviesResponse);
    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        checkNotNull(discoverMoviesResponse);
        mMoviesLocalDataSource.clearMoviesSortTableIfNeeded(discoverMoviesResponse);
    }

    @Override
    public void deleteMovies() {
        mMoviesLocalDataSource.deleteMovies();
    }

    @Override
    public Uri getSortedMoviesUri() {
        return mMoviesLocalDataSource.getSortedMoviesUri();
    }

    // TODO: 8/11/16 4 method này không bao giờ được gọi cho nên nó khi lấy từ sort nó ko có lấy được
    @Override
    public void saveSortByPreference(@Constant.NavigationMode String sort) {
        checkNotNull(sort);
        mMoviesLocalDataSource.saveSortByPreference(sort);
    }
}
