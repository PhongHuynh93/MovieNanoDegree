package dhbk.android.movienanodegree.ui.detailmovie.presenter;

import javax.inject.Inject;

import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;

/**
 * Created by phongdth.ky on 8/9/2016.
 */
public class MovieDetailPresenter implements DetailMovieContract.Presenter {

    private final DetailMovieContract.View mView;

    @Inject
    MovieDetailPresenter(DetailMovieContract.View view) {
        mView = view;
    }

    @Inject
    void setupListeners() {
        mView.setPresenter(this);
    }

    /**
     * set up all of movie detail such as name of movie, video, reviews of image
     */
    @Override
    public void declareMovieDetail() {
        mView.declareView();
        mView.declareMovieInfo();
        mView.declareVideoList();
        mView.declareMovieInfo();
        mView.setCardElevation();
    }

    @Override
    public void start() {
        mView.loadVideosFromNetwork();
        mView.loadReviewsFromNetwork();
        mView.setShowOrHideVideoList();
        mView.setShowOrHideReviewList();
    }


}
