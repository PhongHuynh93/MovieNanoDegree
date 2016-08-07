package dhbk.android.movienanodegree.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.data.MoviesDataSource;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/2/2016.
 */
public class MoviesLocalDataSource extends SortConstant implements MoviesDataSource {
    private static final String TAG = MoviesLocalDataSource.class.getSimpleName();
    private final Context mContext;
    private static final int PAGE_SIZE = 20; // max page load of the list
    /**
     * name for pref follow this link @see <a href="http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get"></a>
     */
    private static final String PREF_SORT_BY_KEY = "sortBy";
    private static final String PREF_SORT_BY_DEFAULT_VALUE = "popularity.desc";

    @Inject
    SharedPreferences mSharedPreferences;

    public MoviesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mContext = context;
        // get the sharepreference
        ((MVPApp)context).getMovieComponent().inject(this);
    }

    /**
     * @param callback
     * @return the current page which has store in content provider
     */
    @Override
    public void getCurrentPage(@NonNull GetCurrentPageCallback callback) {

        String sort = getSortByPreference();
        Uri uri = getSortedMoviesUri();
        if (uri == null) {
            callback.onCurrentPageNotAvailable();
        }

        Cursor movieCursor = mContext.getContentResolver().query(uri, null, null, null, null);
        int currentPage = 1;
        if (movieCursor != null) {
            currentPage = (movieCursor.getCount() - 1) / PAGE_SIZE + 1;
            movieCursor.close();
        }
        callback.onCurrentPageLoaded(sort, currentPage);
    }

    /**
     * save movie id in db
     *
     * @param movieId
     */
    @Override
    public void saveMovieReference(Long movieId) {
        ContentValues entry = new ContentValues();
        entry.put(MoviesContract.COLUMN_MOVIE_ID_KEY, movieId);
        mContext.getContentResolver().insert(getSortedMoviesUri(), entry);
    }


    @Override
    public String getSort() {
        return getSortByPreference();
    }

    /**
     * hàm toContentValues() chuyển từng field trong {@link DiscoverMovie} thành column chính xác trong {@link MoviesContract}
     * @param movie
     * @return
     */
    @Override
    public Uri saveMovie(DiscoverMovie movie) {
        Uri uriMovie = mContext.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movie.toContentValues());
        return uriMovie;
    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        if (discoverMoviesResponse.getPage() == 1) {
            mContext.getContentResolver().delete(getSortedMoviesUri(), null, null);
        }
    }

    @Override
    public void deleteMovies() {
        mContext.getContentResolver().delete(getSortedMoviesUri(), null, null);
    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
        Log.d(TAG, "page == " + discoverMoviesResponse.getPage() + " " +
                discoverMoviesResponse.getMovies().toString());
    }


    // save sort type to pref
    @Override
    public void saveSortByPreference(@SortConstant.NavigationMode String sort) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_SORT_BY_KEY, String.valueOf(sort));
        editor.apply();
    }

    // get sort type from pref
    private String getSortByPreference() {
        String sort = mSharedPreferences.getString(PREF_SORT_BY_KEY, PREF_SORT_BY_DEFAULT_VALUE);
        return sort;
    }

    @Override
    public Uri getSortedMoviesUri() {
        String sort = getSortByPreference();
        switch (sort) {
            case MOST_POPULAR:
                return MoviesContract.MostPopularMovies.CONTENT_URI;
            case HIGHEST_RATED:
                return MoviesContract.HighestRatedMovies.CONTENT_URI;
            case MOST_RATED:
                return MoviesContract.MostRatedMovies.CONTENT_URI;
            default:
                // Signals that a method has been invoked at an illegal or inappropriate time.
                throw new IllegalStateException("Unknown sort.");
        }
    }
}
