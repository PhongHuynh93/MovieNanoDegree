package dhbk.android.movienanodegree.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dhbk.android.movienanodegree.R;

/**
 * Created by huynhducthanhphong on 8/1/16.
 */
public class ListMovieRecyclerViewAdapter extends RecyclerView.Adapter<ListMovieRecyclerViewAdapter.MovieViewHolder> {
    private final Context mContext;

    public ListMovieRecyclerViewAdapter(Context context) {
        mContext = context;
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

    }

    @Override
    public int getItemCount() {
        return 0;
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
        @BindView(R.id.relativelayout_listmovie_maincontent)
        RelativeLayout mRelativelayoutListmovieMaincontent;
        @BindView(R.id.imageview_listmovie_imageofmovie)
        ImageView mImageviewListmovieImageofmovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
