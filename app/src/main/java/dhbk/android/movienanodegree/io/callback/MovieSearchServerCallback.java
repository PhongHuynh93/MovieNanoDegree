package dhbk.android.movienanodegree.io.callback;

import java.util.ArrayList;

import dhbk.android.movienanodegree.io.model.DiscoverMovie;

/**
 * Created by phongdth.ky on 7/15/2016.
 */
public interface MovieSearchServerCallback {
    // when we found the artist from query
    void onMoviesFound(ArrayList<DiscoverMovie> artists);
    // when we not found the artist
    void onFailedSearch();
}
