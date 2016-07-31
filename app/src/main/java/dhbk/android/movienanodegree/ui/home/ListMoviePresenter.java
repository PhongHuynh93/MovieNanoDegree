package dhbk.android.movienanodegree.ui.home;

import javax.inject.Inject;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private final ListMovieContract.View mListMovieView;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    ListMoviePresenter(ListMovieContract.View view) {
        mListMovieView = view;
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
    }
}
