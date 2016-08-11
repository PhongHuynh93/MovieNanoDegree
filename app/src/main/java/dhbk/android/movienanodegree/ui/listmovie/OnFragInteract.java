package dhbk.android.movienanodegree.ui.listmovie;

import dhbk.android.movienanodegree.models.DiscoverMovieResponse;

/**
 * Created by phongdth.ky on 8/8/2016.
 */
public interface OnFragInteract {
    void restartLoader();

    // force to update the database
    void setForceLoad();

    void gotoDetailActivity(DiscoverMovieResponse.DiscoverMovie item);
}
