package dhbk.android.movienanodegree.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.local.MoviesContract;
import dhbk.android.movienanodegree.data.local.SortHelper;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
// TODO: 8/2/16 refactor presenter and put code to reposition
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private static final int PAGE_SIZE = 20; // max page load of the list
    private static final String TAG = ListMoviePresenter.class.getSimpleName();
    private final ListMovieContract.View mListMovieView;
    private final MovieInteractor mMovieInteractor;
    private final MovieReposition mMovieReposition;
    private final Context mContext;
    private volatile boolean loading = false;
    private SortHelper mSortHelper;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    /**
     * @param movieReposition {@link dhbk.android.movienanodegree.data.MovieReposition}
     * @param view            {@link ListMovieViewPagerFragment}
     * @param movieInteractor {@link MovieInteractor}
     */
    @Inject
    ListMoviePresenter(MovieReposition movieReposition, ListMovieContract.View view, MovieInteractor movieInteractor, Context context) {
        mMovieReposition = movieReposition;
        mListMovieView = view;
        mMovieInteractor = movieInteractor;
        mContext = context;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mSortHelper = new SortHelper(mContext);
        mListMovieView.setPresenter(this);
    }

    // fixme: 8/2/2016 not have call yet
    @Override
    public void start() {
        fetchMoviesAsync();
    }

    @Override
    public void fetchMoviesAsync() {
        mListMovieView.makePullToRefreshAppear();
        mListMovieView.getMoviesFromNetwork();
    }

    /**
     * make the network call to get the list of movie
     *
     * @param sort is the way to get the movies from network
     * @param page page of the movie on server
     */
    @Override
    public void callDiscoverMovies(String sort, Integer page) {
        // TODO: 7/31/16 call the interactor to perform search
        mMovieInteractor.performMovieSearch(sort, page, new MovieSearchServerCallback() {
            @Override
            public void onMoviesFound(ArrayList<DiscoverMovie> artists) {
                // TODO: 8/1/16 do something when found the artists
                mListMovieView.loadDataToLists(artists);
                Log.i("test", "onMoviesFound: " + artists.get(0).getOriginalTitle());
            }

            @Override
            public void onFailedSearch() {
                Log.e("test", "onFailedSearch:");
                mListMovieView.infoUserErrorFetchData();
            }
        });
    }

    /**
     * refresh by pulling new data
     */
    @Override
    public void refreshMovies() {
        if (loading) {
            return;
        }
        loading = true;
        String sort = mSortHelper.getSortByPreference();
        callDiscoverMovies(sort, null);
    }

    @Override
    public boolean isLoading() {
        return loading;
    }


    @Override
    public void loadMoreMovies() {
        if (isLoading()) {
            return;
        }
        loading = true;
        String sort = mSortHelper.getSortByPreference();
        Uri uri = mSortHelper.getSortedMoviesUri();
        if (uri == null) {
            return;
        }
        callDiscoverMovies(sort, getCurrentPage(uri) + 1);
    }

    @Override
    public int getCurrentPage(Uri uri) {
        Cursor movieCursor = mContext.getContentResolver().query(uri, null, null, null, null);

        int currentPage = 1;
        if (movieCursor != null) {
            currentPage = (movieCursor.getCount() - 1) / PAGE_SIZE + 1;
            movieCursor.close();
        }
        return currentPage;
    }

    @Override
    public void saveMovieReference(Long movieId) {
        ContentValues entry = new ContentValues();
        entry.put(MoviesContract.COLUMN_MOVIE_ID_KEY, movieId);
        mContext.getContentResolver().insert(mSortHelper.getSortedMoviesUri(), entry);
    }


    @Override
    public Uri saveMovie(DiscoverMovie movie) {
        return mContext.getContentResolver().insert(MoviesContract.MovieEntry.CONTENT_URI, movie.toContentValues());
    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
        Log.d(TAG, "page == " + discoverMoviesResponse.getPage() + " " +
                discoverMoviesResponse.getMovies().toString());
    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        if (discoverMoviesResponse.getPage() == 1) {
            mContext.getContentResolver().delete(mSortHelper.getSortedMoviesUri(), null, null
            );
        }
    }

}
