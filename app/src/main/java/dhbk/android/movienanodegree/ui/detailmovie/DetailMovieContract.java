package dhbk.android.movienanodegree.ui.detailmovie;

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
         * declare reviews recyclerview
         */
        void declareReviewList();

        /**
         * set card elevation
         */
        void setCardElevation();

        /**
         * load list of video from network
         */
        void loadVideosFromNetwork();

        /**
         * load list of reviews from network
         */
        void loadReviewsFromNetwork();

        /**
         * set hide or show videos list depends on datas from video list which has download from network
         */
        void setShowOrHideVideoList();

        /**
         * set hide or show reviews list depends on datas from video list which has download from network
         */
        void setShowOrHideReviewList();
    }

    interface Presenter extends Mvp.BasePresenter {
        /**
         * set up all of movie detail such as name of movie, video, reviews of image
         */
        void declareMovieDetail();
    }
}
