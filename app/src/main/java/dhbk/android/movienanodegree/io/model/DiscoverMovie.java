package dhbk.android.movienanodegree.io.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import dhbk.android.movienanodegree.data.local.MoviesContract;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by huynhducthanhphong on 7/30/16.
 * a child model of {@link DiscoverMovieResponse}
 */
public class DiscoverMovie {
    @Getter @Setter
    @SerializedName("id")
    private long id;

    @Getter @Setter
    @SerializedName("original_title")
    private String originalTitle;

    @Getter @Setter
    @SerializedName("overview")
    private String overview;

    @Getter @Setter
    @SerializedName("release_date")
    private String releaseDate;

    @Getter @Setter
    @SerializedName("poster_path")
    private String posterPath;

    @Getter @Setter
    @SerializedName("popularity")
    private double popularity;

    @Getter @Setter
    @SerializedName("title")
    private String title;

    @Getter @Setter
    @SerializedName("vote_average")
    private double averageVote;

    @Getter @Setter
    @SerializedName("vote_count")
    private long voteCount;

    @Getter @Setter
    @SerializedName("backdrop_path")
    private String backdropPath;

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

    public DiscoverMovie(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static DiscoverMovie fromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry._ID));
        String title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE));
        DiscoverMovie movie = new DiscoverMovie(id, title);
        movie.setOriginalTitle(
                cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setOverview(
                cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)));
        movie.setReleaseDate(
                cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)));
        movie.setPosterPath(
                cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH)));
        movie.setPopularity(
                cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)));
        movie.setAverageVote(
                cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE)));
        movie.setVoteCount(
                cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setBackdropPath(
                cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)));
        return movie;
    }
}
