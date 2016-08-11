package dhbk.android.movienanodegree.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.data.MoviesDataSource;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.util.Constant;
import hugo.weaving.DebugLog;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/2/2016.
 */
public class MoviesLocalDataSource implements MoviesDataSource {
    private final Context mContext;
    private static final int PAGE_SIZE = 20; // max page load of the list
    /**
     * name for pref follow this link @see <a href="http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get"></a>
     */
    private static final String PREF_SORT_BY_KEY = "sortBy";
    private static final String PREF_SORT_BY_DEFAULT_VALUE = Constant.MOST_POPULAR;

    @Inject
    SharedPreferences mSharedPreferences;

    // get context and sharepreference
    public MoviesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mContext = context;
        ((MVPApp) context).getMovieComponent().inject(this);
    }

//    get the current page of movies in local db
    @Override
    public int getCurrentPage() {
        Uri uri = getSortedMoviesUri();
        Cursor movies = mContext.getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        int currentPage = 1;
        // close cursor to avoid memory leaks
        if (movies != null) {
            currentPage = (movies.getCount() - 1) / PAGE_SIZE + 1;
            movies.close();
        }
        return currentPage;
    }

    // save the movie id to table depend on sort
    @Override
    public void saveMovieReference(Long movieId) {
        ContentValues entry = new ContentValues();
        entry.put(MoviesContract.COLUMN_MOVIE_ID_KEY, movieId);
        mContext.getContentResolver().insert(getSortedMoviesUri(), entry);
    }

//    save movie summary to table
    @Override
    public Uri saveMovie(DiscoverMovieResponse.DiscoverMovie movie) {
        Uri uriMovie = mContext.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movie.toContentValues());
        return uriMovie;
    }

//    delete movie id depend of sort
    @Override
    public void deleteMovies() {
        mContext.getContentResolver().delete(getSortedMoviesUri(), null, null);
    }


    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        if (discoverMoviesResponse.getPage() == 1) {
            mContext.getContentResolver().delete(getSortedMoviesUri(), null, null);
        }
    }

//    print movie reponse to logcat
    @DebugLog
    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
    }

    // todo 3 save sort type to pref, test if this method id called (not called)
    @Override
    public void saveSortByPreference(@Constant.NavigationMode String sort) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_SORT_BY_KEY, String.valueOf(sort));
        editor.apply();
    }

    // get sort type from pref
    // TODO: 8/10/16 get the sort value in db
    @Override
    public String getSort() {
        return mSharedPreferences.getString(PREF_SORT_BY_KEY, PREF_SORT_BY_DEFAULT_VALUE);
    }

    // get uri for sort type from pref
    @Override
    public Uri getSortedMoviesUri() {
        // TODO: 8/10/16 1 getSort get error, it's always return popularity
        String sort = getSort();
        switch (sort) {
            case Constant.MOST_POPULAR:
                return MoviesContract.MostPopularMovies.CONTENT_URI;
            case Constant.HIGHEST_RATED:
                return MoviesContract.HighestRatedMovies.CONTENT_URI;
            case Constant.MOST_RATED:
                return MoviesContract.MostRatedMovies.CONTENT_URI;
            default:
                // Signals that a method has been invoked at an illegal or inappropriate time.
                throw new IllegalStateException("Unknown sort.");
        }
    }

    /**
     * get into the db and get the state of fab by compare the movie id
     *
     * @return
     * @param movie
     */
    @Override
    public boolean isFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        boolean favorite = false;
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.Favorites.CONTENT_URI,
                null,
                MoviesContract.COLUMN_MOVIE_ID_KEY + " = " + movie.getId(),
                null,
                null
        );
        if (cursor != null) {
            favorite = cursor.getCount() != 0;
            cursor.close();
        }
        return favorite;
    }

    /**
     * remove favorite from db by compare movie ID
     * @param movie
     */
    @Override
    public void removeFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        mContext.getContentResolver().delete(
                MoviesContract.Favorites.CONTENT_URI,
                MoviesContract.COLUMN_MOVIE_ID_KEY + " = " + movie.getId(),
                null
        );
    }

    /**
     * add favorite to db with movie ID
     * @param movie
     */
    @Override
    public void addFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.COLUMN_MOVIE_ID_KEY, movie.getId());
        mContext.getContentResolver().insert(MoviesContract.Favorites.CONTENT_URI, contentValues);
    }
}
