package dhbk.android.movienanodegree.ui.listmovie.presenter;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.io.MovieInteractor;
import dhbk.android.movienanodegree.io.MovieSearchServerCallback;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieContract;
import dhbk.android.movienanodegree.ui.listmovie.view.ListMovieViewPagerFragment;
import dhbk.android.movienanodegree.util.Constant;

/**
 * Created by phongdth.ky on 8/8/2016.
 */
public class ListMoviePresenter implements ListMovieContract.Presenter {
    private final MovieReposition mMovieReposition;
    private final ListMovieContract.View mListMovieView;
    private final MovieInteractor mMovieInteractor;

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
    ListMoviePresenter(MovieReposition movieReposition, ListMovieContract.View view, MovieInteractor movieInteractor) {
        mMovieReposition = movieReposition;
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
                // : 8/11/16  3 làm cho nó biến mất icon  (result: the icon will dissappear if the network called success or failed)
                mListMovieView.makePullToRefreshDissappear();
                mListMovieView.stopEndlessListener();
            }

            /**
             * a method indicate that we are successful to download and save datas to db.
             * so what to do next.
             */
            @Override
            public void onDownloadAndSaveToDbSuccess() {
                mListMovieView.callRestartLoader();
            }

            /**
             * a method indicate that we are failed to download and save datas to db.
             * so what to do next.
             */
            @Override
            public void onDownloadAndSaveToDbFail() {
                mListMovieView.callRestartLoader();
            }
        });
    }

    // todo 6 when cursor change, update old with new cursor
    @Override
    public void updateListWithCursordata(@Nullable Cursor data) {
        mListMovieView.onCursorLoaded(data);
    }

    @Override
    public void saveSortByPreference(String sort) {
        mMovieReposition.saveSortByPreference(sort);
    }

    @Override
    public void start() {

    }

    /**
     * get the sort type which has saved from db
     * @param tag
     */
    @NonNull
    @Override
    public Uri getContentUri(String tag) {
        switch (tag) {
            case Constant.TAG_VIEWPAGER:
                return mMovieReposition.getSortedMoviesUri();
            case Constant.TAG_FAVORITE:
                // TODO: 8/11/16 return the favorite uri
                return mMovieReposition.getFavMovieUri();
            default:
                throw new IllegalArgumentException("CANNOT FIND URI");
        }
    }

    @Override
    public void loadTask(boolean forceUpdate, boolean firstLoad, String sort) {
        // Simplification for sample: a network reload will be forced on first load.
        loadTasks(forceUpdate || firstLoad, sort);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link MovieReposition}
     */
    private void loadTasks(boolean forceUpdate, String sort) {
        // save the sort
        saveSortByPreference(sort);

        // force to update
        if (forceUpdate) {
//           1  make to icon appear
            mListMovieView.makePullToRefreshAppear();
            // : 8/10/2016 3 change page from null to use function to calculate the current page from databse
            callDiscoverMovies(sort, getCurrentPageFromDb() + 1);
        }

    }

    /**
     * get the current page depend on data in local database.
     */
    @Override
    public int getCurrentPageFromDb() {
        return mMovieReposition.getCurrentPage();
    }
}
