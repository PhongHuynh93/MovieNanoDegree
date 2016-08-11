package dhbk.android.movienanodegree.ui.detailmovie.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.detailmovie.DaggerDetailMovieViewComponent;
import dhbk.android.movienanodegree.dagger.detailmovie.MovieReviewsAdapterModule;
import dhbk.android.movienanodegree.dagger.detailmovie.MovieVideosAdapterModule;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import dhbk.android.movienanodegree.models.MovieVideosResponse;
import dhbk.android.movienanodegree.ui.base.BaseFragment;
import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;
import dhbk.android.movienanodegree.ui.detailmovie.ItemOffsetDecoration;
import dhbk.android.movienanodegree.ui.detailmovie.MovieReviewsAdapter;
import dhbk.android.movienanodegree.ui.detailmovie.MovieVideosAdapter;
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

    @Inject
    MovieVideosAdapter mMovieVideosAdapter;

    @Inject
    MovieReviewsAdapter mMovieReviewsAdapter;

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
     *  load poster image, name of movies
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
//        user rating, auto change color depend on vote count
        movieUserRating.setText(String.format(Locale.US, "%.1f", mMovie.getAverageVote()));
        movieUserRating.setTextColor(getRatingColor(mMovie.getAverageVote()));
//      fixme  release data, remember String.format     <string name="movie_detail_release_date">%s</string>, can change to html format color
        String releaseDate = String.format(getString(R.string.movie_detail_release_date),
                mMovie.getReleaseDate());
        movieReleaseDate.setText(releaseDate);
//        movie description
        movieOverview.setText(mMovie.getOverview());
    }

    /**
     *  declare video recyclerview
     */
    @Override
    public void declareVideoList() {
        // : 8/9/2016 8c listen when click trailer
        mMovieVideosAdapter.setMOnItemClickListener((itemView, position) -> onMovieVideoClicked(position));
        movieVideos.setAdapter(mMovieVideosAdapter);
        movieVideos.setItemAnimator(new DefaultItemAnimator());
        movieVideos.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.dimen.movie_item_offset));
        movieVideos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     *  if you click a trailer among trailers in video list, this method will be called
     * get to youtube site via Intent
     * @see <a href="http://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent">Tutorial</a>
     * @param position position in a list of video.
     */
    @Override
    public void onMovieVideoClicked(int position) {
        // go to youtube intent with data
        MovieVideosResponse.MovieVideo video = mMovieVideosAdapter.getItem(position);
        if (video != null && video.isYoutubeVideo()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
            startActivity(intent);
        }
    }

    /**
     *  declare reviews recyclerview
     */
    @Override
    public void declareReviewList() {
        // : 8/9/2016 9c listen when click the review
        mMovieVideosAdapter.setMOnItemClickListener((itemView, position) -> onMovieReviewClicked(position));
        movieReviews.setAdapter(mMovieReviewsAdapter);
        movieReviews.setItemAnimator(new DefaultItemAnimator());
        movieReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * if you click one review among any reviews, call this method
     * pass the url to another viewer in your device via Intent to see full reviews.
     * @param position
     */
    @Override
    public void onMovieReviewClicked(int position) {
        MovieReviewsResponse.MovieReview review = mMovieReviewsAdapter.getItem(position);
        if (review != null && review.getReviewUrl() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getReviewUrl()));
            startActivity(intent);
        }
    }

    @DebugLog
    @Override
    public void setPresenter(DetailMovieContract.Presenter presenter) {
        checkNotNull(presenter, "Presenter in MovieDetailFragment not be null");
        mPresenter = presenter;
    }

    /**
     * setup the object graph and inject the dependencies needed on this fragment.
     */
    @Override
    protected void injectDependencies() {
        // Create adapter
        DaggerDetailMovieViewComponent
                .builder()
                .movieComponent(((MVPApp) getActivity().getApplication()).getMovieComponent())
                .movieVideosAdapterModule(new MovieVideosAdapterModule())
                .movieReviewsAdapterModule(new MovieReviewsAdapterModule())
                .build()
                .inject(this);
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
     * see if the video adapter whether have empty datas or not,
     * if it has empty data, connect to network to load
     *
     * @return the state that indicate that we can load data or not
     */
    @Override
    public boolean shouldLoadVideosFromNetwork() {
        if (mMovieVideosAdapter.getItemCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * see if the review adapter whether have empty datas or not,
     * if it has empty data, connect to network to load
     *
     * @return the state that indicate that we can load data or not
     */
    @Override
    public boolean shouldLoadReviewsFromNetwork() {
        if (mMovieReviewsAdapter.getItemCount() == 0) {
            return true;
        }
        return false;
    }

    /**
     * get the id of a movie
     *
     * @return movie ID
     */
    @Override
    public long getMovieId() {
        return mMovie.getId();
    }

    /**
     * make the view adapter chagne the data
     *
     * @param movieVideos
     */
    @Override
    public void makeVideoAdapterChangeData(ArrayList<MovieVideosResponse.MovieVideo> movieVideos) {
        mMovieVideosAdapter.setMovieVideos(movieVideos);
    }

    /**
     * make the review adapter change the data
     *
     * @param movieReviews
     */
    @Override
    public void makeReviewAdapterChangeData(ArrayList<MovieReviewsResponse.MovieReview> movieReviews) {
        mMovieReviewsAdapter.setMovieReviews(movieReviews);
    }

    /**
     *  set hide or show videos list depends on datas from video list which has download from network
     */
    @Override
    public void setShowOrHideVideoList() {
        if (mMovieVideosAdapter == null || mMovieVideosAdapter.getItemCount() == 0) {
            cardMovieVideos.setVisibility(View.GONE);
        } else {
            cardMovieVideos.setVisibility(View.VISIBLE);
        }
    }

    /**
     *  set hide or show reviews list depends on datas from video list which has download from network
     */
    @Override
    public void setShowOrHideReviewList() {
        if (mMovieReviewsAdapter == null || mMovieReviewsAdapter.getItemCount() == 0) {
            cardMovieReviews.setVisibility(View.GONE);
        } else {
            cardMovieReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void doThingWhenPauseApp() {

    }

    @Override
    protected void doThingWhenDestroyApp() {

    }
}
