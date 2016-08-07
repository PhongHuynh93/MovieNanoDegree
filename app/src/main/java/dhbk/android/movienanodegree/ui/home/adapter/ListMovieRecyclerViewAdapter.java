package dhbk.android.movienanodegree.ui.home.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.io.model.DiscoverMovie;
import dhbk.android.movienanodegree.ui.Constant;

/**
 * Created by huynhducthanhphong on 8/1/16.
 */
public class ListMovieRecyclerViewAdapter extends CursorRecyclerViewAdapter<ListMovieRecyclerViewAdapter.MovieViewHolder> {
    private final Context mContext;
    private ArrayList<DiscoverMovie> mMovies;

    public ListMovieRecyclerViewAdapter(Context context) {
        mContext = context;
        mMovies = new ArrayList<>(); // create an empty list
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_home, parent, false);

        // Return a new holder instance
        MovieViewHolder viewHolder = new MovieViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        DiscoverMovie discoverMovie = mMovies.get(position);
        holder.mTextviewListmovieNameofmovie.setText(discoverMovie.getOriginalTitle());
        String urlImage = Constant.POSTER_IMAGE_BASE_URL + Constant.POSTER_IMAGE_SIZE + discoverMovie.getPosterPath();
        Picasso.with(mContext).load(urlImage).into(holder.mImageviewListmovieImageofmovie);
    }

    @Override
    public int getItemCount() {
        return mMovies.isEmpty() ? 0 : mMovies.size();
    }

    @Override
    public void onBindViewHolder(MovieViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            Movie movie = Movie.fromCursor(cursor);
            viewHolder.moviePoster.setContentDescription(movie.getTitle());
            Glide.with(context)
                    .load(POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + movie.getPosterPath())
                    .placeholder(new ColorDrawable(context.getResources().getColor(R.color.accent_material_light)))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .crossFade()
                    .into(viewHolder.moviePoster);
        }
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
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

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
