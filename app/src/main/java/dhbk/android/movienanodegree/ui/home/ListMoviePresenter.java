package dhbk.android.movienanodegree.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.MoviesDataSource;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.io.model.DiscoverMovieResponse;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
// TODO: 8/2/16 refactor presenter and put code to reposition
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private static final String TAG = ListMoviePresenter.class.getSimpleName();
    private final ListMovieContract.View mListMovieView;
    private final MovieInteractor mMovieInteractor;
    private final MovieReposition mMovieReposition;
    private final Context mContext;
    private volatile boolean loading = false;
    private Uri mContentUri;

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
//            @Override
//            public void onMoviesFound(ArrayList<DiscoverMovie> artists) {
//                // TODO: 8/1/16 do something when found the artists
//                mListMovieView.loadDataToLists(artists);
//                Log.i("test", "onMoviesFound: " + artists.get(0).getOriginalTitle());
//            }
//
//            @Override
//            public void onFailedSearch() {
//                Log.e("test", "onFailedSearch:");
//                mListMovieView.infoUserErrorFetchData();
//            }
//

            /**
             * change state of loading -> turn on loading icon
             * turn off litener for english scrolling
             * @param b indicate the state of loading
             */
            @Override
            public void onSetLoading(boolean b) {
                loading = b;
                mListMovieView.makePullToRefreshDissappear();
                mListMovieView.stopEndlessListener();
            }

            /**
             * a method indicate that we are successful to download and save datas to db.
             * so what to do next.
             */
            @Override
            public void onDownloadAndSaveToDbSuccess() {
                mListMovieView.updateLayout();
            }

            /**
             * a method indicate that we are failed to download and save datas to db.
             * so what to do next.
             */
            @Override
            public void onDownloadAndSaveToDbFail() {
                mListMovieView.infoUserErrorFetchData();
                mListMovieView.updateLayout();
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
        callDiscoverMovies(mMovieReposition.getSort(), null);
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
        mMovieReposition.getCurrentPage(new MoviesDataSource.GetCurrentPageCallback() {
            @Override
            public void onCurrentPageLoaded(String sort, int currentPage) {
                callDiscoverMovies(sort, currentPage + 1);
            }

            @Override
            public void onCurrentPageNotAvailable() {
//                int currentPage = 1;
//                callDiscoverMovies(sort, currentPage + 1);
            }
        });
    }

    @Override
    public void saveMovieReference(Long movieId) {
        mMovieReposition.saveMovieReference(movieId);
    }


    @Override
    public void saveMovie(DiscoverMovie movie) {
        mMovieReposition.saveMovie(movie);
    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {
        Log.d(TAG, "page == " + discoverMoviesResponse.getPage() + " " +
                discoverMoviesResponse.getMovies().toString());
    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {
        if (discoverMoviesResponse.getPage() == 1) {
            mMovieReposition.deleteMovies();
        }
    }

    public Uri getContentUri() {
        return mMovieReposition.getSortedMoviesUri();
    }

    @Override
    public void updateListWithCursordata(Cursor data) {
        mListMovieView.onCursorLoaded(data);
        if (data == null || data.getCount() == 0) {
            refreshMovies();
        }
        mListMovieView.updateLayout();
    }
}
