package dhbk.android.movienanodegree.ui.home;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.data.local.SortHelper;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public class ListMoviePresenter implements ListMovieContract.Presenter {
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
     *
     * @param movieReposition {@link dhbk.android.movienanodegree.data.MovieReposition}
     * @param view {@link ListMovieViewPagerFragment}
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
        String sort = mSortHelper.getSortByPreference().toString();
        callDiscoverMovies(sort, null);
    }


}
