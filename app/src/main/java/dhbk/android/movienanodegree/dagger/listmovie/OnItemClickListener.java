package dhbk.android.movienanodegree.dagger.listmovie;

import android.view.View;

/**
 * Created by huynhducthanhphong on 8/7/16.
 * contain callback when click on list movie {@link dhbk.android.movienanodegree.ui.listmovie.ListMovieRecyclerViewAdapter}
 */
public interface OnItemClickListener {
    void onItemClick(View itemView, int position);
}
