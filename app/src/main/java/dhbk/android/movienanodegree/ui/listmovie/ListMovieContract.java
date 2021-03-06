package dhbk.android.movienanodegree.ui.listmovie;

import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public interface ListMovieContract {
    interface View extends Mvp.BaseView<Presenter> {


        // make the icon appear
        void makePullToRefreshAppear();

        /**
         * make the icon pull to refresh dissappear
         */
        void makePullToRefreshDissappear();

        void updateLayout();

        /**
         * stop the endless listener
         */
        void stopEndlessListener();

        void onCursorLoaded(@Nullable Cursor data);

        /**
         * restart loader to load datas from local database again
         */
        void callRestartLoader();

        /**
         * save the current tab of the viewpager and force load datas from network
         */
        void setForceload();
    }

    interface Presenter extends Mvp.BasePresenter {
        void callDiscoverMovies(String sort, Integer page);

        void updateListWithCursordata(@Nullable Cursor data);

        void saveSortByPreference(String sort);

        /**
         * get the sort type which has saved from db
         * @param tag
         */
        @NonNull
        Uri getContentUri(String tag);

        void loadTask(boolean forceUpdate, boolean firstLoad, String sort);

        /**
         * get the current page depend on data in local database.
         */
        int getCurrentPageFromDb();
    }
}
