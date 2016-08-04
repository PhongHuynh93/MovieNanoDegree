package dhbk.android.movienanodegree.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.StringRes;

import javax.inject.Inject;

import dhbk.android.movienanodegree.MVPApp;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/2/2016.
 * Used this class for insert and get sort type from sharedPreference
 */
public class SortHelper extends SortConstant{
    /**
     * name for pref follow this link @see <a href="http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get"></a>
     */
    private static final String PREF_SORT_BY_KEY = "sortBy";
    private static final String PREF_SORT_BY_DEFAULT_VALUE = "popularity.desc";

    @Inject
    SharedPreferences mSharedPreferences;

    public SortHelper(Context context) {
        checkNotNull(context);
        // get the sharepreference
        ((MVPApp)context).getMovieComponent().inject(this);
    }

    // save sort type to pref
    public void saveSortByPreference(@NavigationMode StringRes sort) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREF_SORT_BY_KEY, String.valueOf(sort));
        editor.apply();
    }

    // get sort type from pref
    public String getSortByPreference() {
        String sort = mSharedPreferences.getString(PREF_SORT_BY_KEY, PREF_SORT_BY_DEFAULT_VALUE);
        return sort;
    }

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
