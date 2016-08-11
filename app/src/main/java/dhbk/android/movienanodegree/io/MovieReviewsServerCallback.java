package dhbk.android.movienanodegree.io;

import java.util.ArrayList;

import dhbk.android.movienanodegree.models.MovieReviewsResponse;

/**
 * Created by phongdth.ky on 8/10/2016.
 */
public interface MovieReviewsServerCallback {
    /**
     * update new datas in list
     * @param movieReviews new datas need to swap in
     */
    void onUpdateReviewAdapter(ArrayList<MovieReviewsResponse.MovieReview> movieReviews);

    /**
     * find out if the adapter is empty or not, so we can hide or show it.
     */
    void onSetShowOrHideReviewList();

}
