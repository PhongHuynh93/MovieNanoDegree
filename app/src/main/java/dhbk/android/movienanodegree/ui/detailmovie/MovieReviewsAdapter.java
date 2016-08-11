package dhbk.android.movienanodegree.ui.detailmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.OnItemClickListener;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by huynhducthanhphong on 8/9/16.
 */
public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewViewHolder> {
    private final Context mContext;

    @Getter
    private ArrayList<MovieReviewsResponse.MovieReview> mMovieReviewList;

    @Setter
    private OnItemClickListener mOnItemClickListener;

    public MovieReviewsAdapter(Context context) {
        checkNotNull(context);
        mMovieReviewList = new ArrayList<>();
        mContext = context;
    }

    // : 8/10/2016
    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_review, parent, false);
        return new MovieReviewViewHolder(itemView, mOnItemClickListener);
    }

    // : 8/10/2016
    @Override
    public void onBindViewHolder(MovieReviewViewHolder holder, int position) {
        if (mMovieReviewList.size() == 0) {
            return;
        }
        MovieReviewsResponse.MovieReview review = mMovieReviewList.get(position);
        holder.mTextviewReviewAdapterContent.setText(review.getContent());
        holder.mTextviewReviewAdapterAuthor.setText(review.getAuthor());
    }

    // : 8/10/2016
    @Override
    public int getItemCount() {
        return mMovieReviewList.size();
    }

    // : 8/10/2016 set a list of movie reviews
    public void setMovieReviews(ArrayList<MovieReviewsResponse.MovieReview> movieReviews) {
        mMovieReviewList = movieReviews;
        notifyDataSetChanged();
    }

    // : 8/10/2016 get one item depend on position
    public MovieReviewsResponse.MovieReview getItem(int position) {
        if (mMovieReviewList == null || position < 0 || position > mMovieReviewList.size()) {
            return null;
        }
        return mMovieReviewList.get(position);
    }


    // : 8/10/2016
    public static class MovieReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textview_review_adapter_content)
        TextView mTextviewReviewAdapterContent;
        @BindView(R.id.textview_review_adapter_author)
        TextView mTextviewReviewAdapterAuthor;

        public MovieReviewViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }

}
