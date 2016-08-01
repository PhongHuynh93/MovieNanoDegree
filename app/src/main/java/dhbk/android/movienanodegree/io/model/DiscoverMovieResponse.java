package dhbk.android.movienanodegree.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by huynhducthanhphong on 7/30/16.
 */
public class DiscoverMovieResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<DiscoverMovie> results;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private long totalResults;

    public int getPage() {
        return page;
    }

    public ArrayList<DiscoverMovie> getMovies() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }

}
