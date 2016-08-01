package dhbk.android.movienanodegree.ui.home;

import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.callback.MovieSearchServerCallback;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private final ListMovieContract.View mListMovieView;
    private final MovieInteractor mMovieInteractor;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    // FIXME: 8/1/16 not call presenter constructor
    @Inject
    ListMoviePresenter(ListMovieContract.View view, MovieInteractor movieInteractor) {
        mListMovieView = view;
        mMovieInteractor = movieInteractor;
    }

    /**
     * Method injection is used here to safely reference {@code this} after the object is created.
     * For more information, see Java Concurrency in Practice.
     */
    @Inject
    void setupListeners() {
        mListMovieView.setPresenter(this);
    }

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
}
