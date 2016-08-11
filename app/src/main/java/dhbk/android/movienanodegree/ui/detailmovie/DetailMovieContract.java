package dhbk.android.movienanodegree.ui.detailmovie;

import java.util.ArrayList;

import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import dhbk.android.movienanodegree.models.MovieVideosResponse;
import dhbk.android.movienanodegree.ui.base.Mvp;

/**
 * Created by phongdth.ky on 8/9/2016.
 */
public interface DetailMovieContract {
    interface View extends Mvp.BaseView<Presenter> {

        /**
         * get the movie values from intent
         */
        void getIntent();

        /**
         * declare view
         */
        void declareView();

        /**
         * load poster image, name of movies
         */
        void declareMovieInfo();

        /**
         * declare video recyclerview
         */
        void declareVideoList();

        /**
         * if you click a trailer among trailers in video list, this method will be called
         *
         * @param position
         */
        void onMovieVideoClicked(int position);

        /**
         * declare reviews recyclerview
         */
        void declareReviewList();

        void onMovieReviewClicked(int position);

        /**
         * set hide or show videos list depends on datas from video list which has download from network
         */
        void setShowOrHideVideoList();

        /**
         * set hide or show reviews list depends on datas from video list which has download from network
         */
        void setShowOrHideReviewList();

        /**
         * see if the video adapter whether have empty datas or not,
         * if it has empty data, connect to network to load
         *
         * @return the state that indicate that we can load data or not
         */
        boolean shouldLoadVideosFromNetwork();

        /**
         * see if the review adapter whether have empty datas or not,
         * if it has empty data, connect to network to load
         *
         * @return the state that indicate that we can load data or not
         */
        boolean shouldLoadReviewsFromNetwork();

        /**
         * get the id of a movie
         *
         * @return movie ID
         */
        long getMovieId();

        /**
         * make the view adapter chagne the data
         *
         * @param movieVideos
         */
        void makeVideoAdapterChangeData(ArrayList<MovieVideosResponse.MovieVideo> movieVideos);


        /**
         * make the review adapter change the data
         */
        void makeReviewAdapterChangeData(ArrayList<MovieReviewsResponse.MovieReview> movieReviews);
    }

    interface Presenter extends Mvp.BasePresenter {
        /**
         * set up all of movie detail such as name of movie, video, reviews of image
         */
        void declareMovieDetail();

        /**
         * get into the db and get the state of fab
         *
         * @param movie
         * @return
         */
        boolean isFavorite(DiscoverMovieResponse.DiscoverMovie movie);

        /**
         * remove favorite from db by compare movie ID
         *
         * @param movie
         */
        void removeFavorite(DiscoverMovieResponse.DiscoverMovie movie);

        /**
         * add favorite to db with movie ID
         *
         * @param movie
         */
        void addFavorite(DiscoverMovieResponse.DiscoverMovie movie);
    }
}
