package dhbk.android.movienanodegree.ui.listmovie;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.dagger.listmovie.OnItemClickListener;
import dhbk.android.movienanodegree.models.DiscoverMovieResponse.DiscoverMovie;
import dhbk.android.movienanodegree.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huynhducthanhphong on 8/1/16.
 */
public class ListMovieRecyclerViewAdapter extends CursorRecyclerViewAdapter<ListMovieRecyclerViewAdapter.MovieViewHolder> {
    private final Context mContext;
    private OnItemClickListener onItemClickListener;

    public ListMovieRecyclerViewAdapter(Context context, Cursor cursor) {
        super(cursor);
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_home, parent, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(contactView, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            DiscoverMovie movie = DiscoverMovie.fromCursor(cursor);

            viewHolder.mTextviewListmovieNameofmovie.setText(movie.getOriginalTitle());
//            viewHolder.mTextviewListmovieTypeofmovie.setText(movie.getOriginalTitle());
//            viewHolder.mTextviewListmovieLengthofmovie.setText(movie.getOriginalTitle());
            viewHolder.mfabVoteAverate.setText("" + movie.getAverageVote());
            viewHolder.mTextViewListMovieViewCount.setText("" + movie.getVoteCount());
            viewHolder.mTextviewListmovieDescriptionofmovie.setText(movie.getOverview());

            String urlImage = Constant.POSTER_IMAGE_BASE_URL + Constant.POSTER_IMAGE_SIZE + movie.getPosterPath();

            /**
             * diskCacheStrategy.ALL cache full size of image and then resize it.
             * Use for resize image for detail list movie activity.
             */
            Glide.with(mContext)
                    .load(urlImage)
                    .placeholder(new ColorDrawable(ContextCompat.getColor(mContext, R.color.accent_material_light)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .crossFade()
                    .into(viewHolder.mImageviewListmovieImageofmovie);
        }
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnItemClickListener onItemClickListener;
        @BindView(R.id.textview_listmovie_nameofmovie)
        TextView mTextviewListmovieNameofmovie;
        @BindView(R.id.textview_listmovie_typeofmovie)
        TextView mTextviewListmovieTypeofmovie;
        @BindView(R.id.textview_listmovie_lengthofmovie)
        TextView mTextviewListmovieLengthofmovie;
        @BindView(R.id.textview_listmovie_descriptionofmovie)
        TextView mTextviewListmovieDescriptionofmovie;
        @BindView(R.id.imageview_listmovie_imageofmovie)
        ImageView mImageviewListmovieImageofmovie;
        @BindView(R.id.textview_listmovie_vote)
        TextView mfabVoteAverate;
        @BindView(R.id.textview_listmovie_viewcount)
        TextView mTextViewListMovieViewCount;


        public MovieViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    @Nullable
    public DiscoverMovie getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor == null) {
            return null;
        }
        if (position < 0 || position > cursor.getCount()) {
            return null;
        }
        cursor.moveToFirst();
        for (int i = 0; i < position; i++) {
            cursor.moveToNext();
        }
        return DiscoverMovie.fromCursor(cursor);
    }

}
