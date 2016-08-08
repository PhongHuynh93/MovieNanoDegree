package dhbk.android.movienanodegree.ui.listmovie;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.io.MovieInteractor;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;

import javax.inject.Inject;

/**
 * Created by phongdth.ky on 8/8/2016.
 */
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private final MovieReposition mMovieReposition;
    private final ListMovieContract.View mListMovieView;
    private final MovieInteractor mMovieInteractor;
    private final Context mContext;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    /**
     * @param movieReposition {@link MovieReposition}
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

    @Override
    public void fetchMoviesAsync() {

    }

    /**
     * make the network call to get the list of movie depend on type of sort and the integer
     *
     * @param sort
     * @param page
     */
    @Override
    public void callDiscoverMovies(String sort, int page) {

    }

    @Override
    public void refreshMovies() {

    }

    /**
     * determine whether the screen is fetching data or not
     */
    @Override
    public boolean isLoading() {
        return false;
    }

    /**
     * load more movie
     */
    @Override
    public void loadMoreMovies() {

    }

    /**
     * save a id movies in db
     *
     * @param movieId
     */
    @Override
    public void saveMovieReference(long movieId) {

    }

    /**
     * save a movie in db
     *
     * @param movie
     * @return
     */
    @Override
    public void saveMovie(DiscoverMovieResponse.DiscoverMovie movie) {

    }

    @Override
    public void logResponse(DiscoverMovieResponse discoverMoviesResponse) {

    }

    @Override
    public void clearMoviesSortTableIfNeeded(DiscoverMovieResponse discoverMoviesResponse) {

    }

    @Override
    public void updateListWithCursordata(@Nullable Cursor data) {
        mListMovieView.onCursorLoaded(data);
    }

    @Override
    public void saveSortByPreference(String sort) {

    }

    @Override
    public void start() {

    }

    /**
     * get the sort type which has saved from db
     */
    @NonNull
    @Override
    public Uri getContentUri() {
        return mMovieReposition.getSortedMoviesUri();
    }
}
