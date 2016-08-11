package dhbk.android.movienanodegree.ui.detailmovie.presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import dhbk.android.movienanodegree.data.MovieReposition;
import dhbk.android.movienanodegree.io.MovieInteractor;
import dhbk.android.movienanodegree.io.MovieReviewsServerCallback;
import dhbk.android.movienanodegree.io.MovieVideosServerCallback;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import dhbk.android.movienanodegree.models.MovieVideosResponse;
import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;

/**
 * Created by phongdth.ky on 8/9/2016.
 */
public class MovieDetailPresenter implements DetailMovieContract.Presenter {

    private final DetailMovieContract.View mView;
    private final MovieInteractor mMovieInteractor;
    private final MovieReposition mMovieReposition;

    @Inject
    MovieDetailPresenter(DetailMovieContract.View view, MovieInteractor movieInteractor, MovieReposition movieReposition) {
        mView = view;
        mMovieInteractor = movieInteractor;
        mMovieReposition = movieReposition;
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
//        mView.setCardElevation();
    }

    @Override
    public void start() {
        if (mView.shouldLoadVideosFromNetwork()){
            mMovieInteractor.performLoadVideosFromNetwork(mView.getMovieId(), new MovieVideosServerCallback() {
                @Override
                public void onSetShowOrHideVideoList() {
                    mView.setShowOrHideVideoList();
                }

                @Override
                public void onUpdateVideoAdapter(ArrayList<MovieVideosResponse.MovieVideo> movieVideos) {
                    mView.makeVideoAdapterChangeData(movieVideos);
                }
            });
        }

        if (mView.shouldLoadReviewsFromNetwork()) {
            mMovieInteractor.performLoadReviewsFromNetwork(mView.getMovieId(), new MovieReviewsServerCallback() {
                @Override
                public void onUpdateReviewAdapter(ArrayList<MovieReviewsResponse.MovieReview> movieReviews) {
                    mView.makeReviewAdapterChangeData(movieReviews);
                }

                @Override
                public void onSetShowOrHideReviewList() {
                    mView.setShowOrHideReviewList();
                }
            });
        }
        mView.setShowOrHideVideoList();
        mView.setShowOrHideReviewList();
    }

    /**
     * get into the db and get the state of fab
     *
     * @return
     * @param movie
     */
    @Override
    public boolean isFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        return mMovieReposition.isFavorite(movie);
    }

    /**
     * remove favorite from db by compare movie ID
     * @param movie
     */
    @Override
    public void removeFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        mMovieReposition.removeFavorite(movie);
    }

    /**
     * add favorite to db with movie ID
     * @param movie
     */
    @Override
    public void addFavorite(DiscoverMovieResponse.DiscoverMovie movie) {
        mMovieReposition.addFavorite(movie);
    }
}
