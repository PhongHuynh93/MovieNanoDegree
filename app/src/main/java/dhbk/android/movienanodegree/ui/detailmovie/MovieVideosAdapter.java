package dhbk.android.movienanodegree.ui.detailmovie;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.OnItemClickListener;
import dhbk.android.movienanodegree.models.MovieVideosResponse;
import dhbk.android.movienanodegree.util.Constant;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by phongdth.ky on 8/9/2016.
 */
public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideoViewHolder>{
    private final Context mContext;

    @Getter
    private ArrayList<MovieVideosResponse.MovieVideo> mMovieVideoList;

    @Setter
    private OnItemClickListener mOnItemClickListener;


    public MovieVideosAdapter(Context context) {
        this.mContext = context;
        mMovieVideoList = new ArrayList<>();
    }


    @Override
    public MovieVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_video, parent, false);
        return new MovieVideoViewHolder(itemView, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(MovieVideoViewHolder holder, int position) {
//        if empty list, do nothing
        if (mMovieVideoList.size() == 0) {
            return;
        }

        MovieVideosResponse.MovieVideo video = mMovieVideoList.get(position);
        if (video.isYoutubeVideo()) {
            Glide.with(mContext)
                    .load(String.format(Constant.YOUTUBE_THUMBNAIL, video.getKey()))
                    .placeholder(new ColorDrawable(mContext.getResources().getColor(R.color.accent_material_light)))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .centerCrop()
                    .crossFade()
                    .into(holder.movieVideoThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return mMovieVideoList.size();
    }

    // change the data in adapter and notify it.
    public void setMovieVideos(@Nullable ArrayList<MovieVideosResponse.MovieVideo> movieVideos) {
        mMovieVideoList = movieVideos;
        notifyDataSetChanged();
    }
    /**
     * get a video from list
     * @param position the videos position in list
     * @return can be nullable if not found any videos underneath
     */
    @Nullable
    public MovieVideosResponse.MovieVideo getItem(int position) {
        if (mMovieVideoList == null || position < 0 || position > mMovieVideoList.size()) {
            return null;
        }
        return mMovieVideoList.get(position);
    }

    public class MovieVideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_video_thumbnail)
        ImageView movieVideoThumbnail;

        //        when click , get the callback
        public MovieVideoViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            });
        }
    }
}
