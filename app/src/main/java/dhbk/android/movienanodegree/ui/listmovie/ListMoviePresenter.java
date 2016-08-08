package dhbk.android.movienanodegree.ui.listmovie;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.io.MovieInteractor;
import dhbk.android.movienanodegree.io.MovieSearchServerCallback;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;

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
        // : 7/31/16 call the interactor to perform search
        mMovieInteractor.performMovieSearch(sort, page, new MovieSearchServerCallback() {
            /**
             * change state of loading -> turn on loading icon
             * turn off litener for english scrolling
             * @param b indicate the state of loading
             */
            @Override
            public void onSetLoading(boolean b) {
//                implement this todo
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
