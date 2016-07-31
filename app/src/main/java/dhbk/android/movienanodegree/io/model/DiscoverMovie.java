package dhbk.android.movienanodegree.io.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhducthanhphong on 7/30/16.
 * a child model of {@link DiscoverMovieResponse}
 */
public class DiscoverMovie {
    @SerializedName("id")
    private long id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double averageVote;

    @SerializedName("vote_count")
    private long voteCount;

    @SerializedName("backdrop_path")
    private String backdropPath;

}
