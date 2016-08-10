package dhbk.android.movienanodegree.ui.detailmovie.view;


import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Locale;

import butterknife.BindView;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;
import dhbk.android.movienanodegree.util.Constant;
import hugo.weaving.DebugLog;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends BaseFragment implements DetailMovieContract.View {
    private static final String ARG_MOVIE = "arg_movie";
    private DetailMovieContract.Presenter mPresenter;
    private DiscoverMovieResponse.DiscoverMovie mMovie;


    @BindView(R.id.image_movie_detail_poster)
    ImageView movieImagePoster;
    @BindView(R.id.text_movie_original_title)
    TextView movieOriginalTitle;
    @BindView(R.id.text_movie_user_rating)
    TextView movieUserRating;
    @BindView(R.id.text_movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.text_movie_overview)
    TextView movieOverview;
    @BindView(R.id.card_movie_detail)
    CardView cardMovieDetail;
    @BindView(R.id.card_movie_overview)
    CardView cardMovieOverview;

    @BindView(R.id.card_movie_videos)
    CardView cardMovieVideos;
    @BindView(R.id.movie_videos)
    RecyclerView movieVideos;

    @BindView(R.id.card_movie_reviews)
    CardView cardMovieReviews;
    @BindView(R.id.movie_reviews)
    RecyclerView movieReviews;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    // : 8/9/2016 5 set the fragment  with movie
    @DebugLog
    public static MovieDetailFragment newInstance(@NonNull DiscoverMovieResponse.DiscoverMovie movie) {
        checkNotNull(movie);
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_movie_detail;
    }

    @Override
    protected boolean hasToolbar() {
        return false;
    }


    @Override
    protected void initView() {
        mPresenter.declareMovieDetail();
    }


    /**
     * declare view
     */
    @Override
    public void declareView() {
    }

    @ColorInt
    private int getRatingColor(double averageVote) {
        if (averageVote >= Constant.VOTE_PERFECT) {
            return ContextCompat.getColor(getContext(), R.color.vote_perfect);
        } else if (averageVote >= Constant.VOTE_GOOD) {
            return ContextCompat.getColor(getContext(), R.color.vote_good);
        } else if (averageVote >= Constant.VOTE_NORMAL) {
            return ContextCompat.getColor(getContext(), R.color.vote_normal);
        } else {
            return ContextCompat.getColor(getContext(), R.color.vote_bad);
        }
    }

    /**
     * todo load poster image, name of movies
     */
    @Override
    public void declareMovieInfo() {
        // add poster image
        Glide.with(this)
                .load(Constant.POSTER_IMAGE_BASE_URL + Constant.POSTER_IMAGE_SIZE + mMovie.getPosterPath())
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(movieImagePoster);
//        title
        movieOriginalTitle.setText(mMovie.getOriginalTitle());
//        user rating
        movieUserRating.setText(String.format(Locale.US, "%.1f", mMovie.getAverageVote()));
        movieUserRating.setTextColor(getRatingColor(mMovie.getAverageVote()));
//        release data
        String releaseDate = String.format(getString(R.string.movie_detail_release_date),
                mMovie.getReleaseDate());
        movieReleaseDate.setText(releaseDate);
//        movie description
        movieOverview.setText(mMovie.getOverview());
    }

    /**
     * todo declare video recyclerview
     */
    @Override
    public void declareVideoList() {

    }

    /**
     * todo declare reviews recyclerview
     */
    @Override
    public void declareReviewList() {

    }

    /**
     * todo set card elevation
     */
    @Override
    public void setCardElevation() {

    }

    @DebugLog
    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        checkNotNull(presenter, "Presenter in MovieDetailFragment not be null");
        mPresenter = presenter;
    }

    /**
     * todo etup the object graph and inject the dependencies needed on this fragment.
     */
    @Override
    protected void injectDependencies() {

    }


    @Override
    protected void doThingWhenCreateApp() {
        getIntent();
    }

    // : 8/9/2016 6  get the movie values
    @Override
    public void getIntent() {
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    protected void doThingWhenActivityCreated() {

    }

    @Override
    protected void doThingWhenResumeApp() {
        mPresenter.start();
        // : 8/9/2016 11 load list of video from network
        // : 8/9/2016 12 load list of reviews from network
        // : 8/9/2016 13 set hide or show videos
        // : 8/9/2016 14 set hide or show reviews
    }

    /**
     * todo load list of video from network
     */
    @Override
    public void loadVideosFromNetwork() {

    }

    /**
     * todo load list of reviews from network
     */
    @Override
    public void loadReviewsFromNetwork() {

    }

    /**
     * todo set hide or show videos list depends on datas from video list which has download from network
     */
    @Override
    public void setShowOrHideVideoList() {

    }

    /**
     * todo set hide or show reviews list depends on datas from video list which has download from network
     */
    @Override
    public void setShowOrHideReviewList() {

    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }
}
