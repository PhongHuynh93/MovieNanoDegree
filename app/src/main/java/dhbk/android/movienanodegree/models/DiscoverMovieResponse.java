package dhbk.android.movienanodegree.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import dhbk.android.movienanodegree.data.local.MoviesContract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by huynhducthanhphong on 7/30/16.
 * http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
 */
public class DiscoverMovieResponse {
    @Getter
    @SerializedName("page")
    private int page;

    @Getter
    @SerializedName("results")
    private ArrayList<DiscoverMovie> results;

    @Getter
    @SerializedName("total_pages")
    private int totalPages;

    @Getter
    @SerializedName("total_results")
    private long totalResults;

//    public DiscoverMovieResponse(int page) {
//        this.page = page;
//    }


    // define a constructor for each field
    @AllArgsConstructor
    public static class DiscoverMovie implements Parcelable {
        @Getter
        @Setter
        @SerializedName("id")
        private long id;

        @Getter
        @Setter
        @SerializedName("original_title")
        private String originalTitle;

        @Getter
        @Setter
        @SerializedName("overview")
        private String overview;

        @Getter
        @Setter
        @SerializedName("release_date")
        private String releaseDate;

        @Getter
        @Setter
        @SerializedName("poster_path")
        private String posterPath;

        @Getter
        @Setter
        @SerializedName("popularity")
        private double popularity;

        @Getter
        @Setter
        @SerializedName("title")
        private String title;

        @Getter
        @Setter
        @SerializedName("vote_average")
        private double averageVote;

        @Getter
        @Setter
        @SerializedName("vote_count")
        private long voteCount;

        @Getter
        @Setter
        @SerializedName("backdrop_path")
        private String backdropPath;

        protected DiscoverMovie(Parcel in) {
            id = in.readLong();
            originalTitle = in.readString();
            overview = in.readString();
            releaseDate = in.readString();
            posterPath = in.readString();
            popularity = in.readDouble();
            title = in.readString();
            averageVote = in.readDouble();
            voteCount = in.readLong();
            backdropPath = in.readString();
        }

        public static final Creator<DiscoverMovie> CREATOR = new Creator<DiscoverMovie>() {
            @Override
            public DiscoverMovie createFromParcel(Parcel in) {
                return new DiscoverMovie(in);
            }

            @Override
            public DiscoverMovie[] newArray(int size) {
                return new DiscoverMovie[size];
            }
        };

        /**
         * get the field to one object
         *
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


        public static DiscoverMovie fromCursor(Cursor cursor) {
            long id = cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_TITLE));
            String originalTitle =
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
            String overview =
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW));
            String releaseDate =
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE));
            String posterPath =
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER_PATH));
            double popularity =
                    cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY));
            double averageVote =
                    cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE));
            long voteCount =
                    cursor.getLong(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT));
            String backdropPath =
                    cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH));
            return new DiscoverMovie(id, originalTitle, overview, releaseDate, posterPath, popularity, title, averageVote, voteCount, backdropPath);
        }

        /**
         * watch parcel tutorial
         * http://guides.codepath.com/android/Using-Parcelable
         * @return
         */
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(id);
            parcel.writeString(originalTitle);
            parcel.writeString(overview);
            parcel.writeString(releaseDate);
            parcel.writeString(posterPath);
            parcel.writeDouble(popularity);
            parcel.writeString(title);
            parcel.writeDouble(averageVote);
            parcel.writeLong(voteCount);
            parcel.writeString(backdropPath);
        }
    }
}
