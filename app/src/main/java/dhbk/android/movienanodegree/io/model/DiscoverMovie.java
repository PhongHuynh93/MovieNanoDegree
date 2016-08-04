package dhbk.android.movienanodegree.io.model;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import dhbk.android.movienanodegree.data.local.MoviesContract;

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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }


    /**
     * get the field to one object
     * @return
     */
    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MoviesContract.MovieEntry._ID, id);
        values.put(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        values.put(MoviesContract.MovieEntry.COLUMN_OVERVIEW, overview);
        values.put(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate);
        values.put(MoviesContract.MovieEntry.COLUMN_POSTER_PATH, posterPath);
        values.put(MoviesContract.MovieEntry.COLUMN_POPULARITY, popularity);
        values.put(MoviesContract.MovieEntry.COLUMN_TITLE, title);
        values.put(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE, averageVote);
        values.put(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT, voteCount);
        values.put(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
        return values;
    }
}
