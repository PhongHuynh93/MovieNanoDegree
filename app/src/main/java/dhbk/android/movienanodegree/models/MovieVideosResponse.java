package dhbk.android.movienanodegree.models;

/**
 * Created by huynhducthanhphong on 7/30/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Locale;

import dhbk.android.movienanodegree.util.Constant;
import lombok.Getter;

/**
 * see http://docs.themoviedb.apiary.io/#reference/movies/movieidvideos/get
 * for field in JSON response for movie video
 *
 * Get the videos (trailers, teasers, clips, etc...) for a specific movie id.
 * http://api.themoviedb.org/3/movie/id/videos
 * {
 "id": 550,
 "results": [
     {
         "id": "533ec654c3a36854480003eb",
         "iso_639_1": "en",
         "key": "SUXWAEX2jlg",
         "name": "Trailer 1",
         "site": "YouTube",
         "size": 720,
         "type": "Trailer"
     }
 ]
 }
 */
public class MovieVideosResponse {
    @Getter
    @SerializedName("id")
    private long movieId;

    @Getter
    @SerializedName("results")
    private ArrayList<MovieVideo> results;

//    public MovieVideosResponse(long movieId, ArrayList<MovieVideo> results) {
//        this.movieId = movieId;
//        this.results = results;
//    }

    public static class MovieVideo implements Parcelable {
        @Getter
        @SerializedName("id")
        private String videoId;

        @Getter
        @SerializedName("iso_639_1")
        private String languageCode;

        @Getter
        @SerializedName("iso_3166_1")
        private String countryCode;

        // declare the key to go to exactly where the video will be played
        @Getter
        @SerializedName("key")
        private String key;

        @Getter
        @SerializedName("name")
        private String name;

        // declare which site contains this video clip
        @Getter
        @SerializedName("site")
        private String site;

        @Getter
        @SerializedName("size")
        private int size;

        @Getter
        @SerializedName("type")
        private String type;

        // find out the site of movies whether it if from youtube or not.
        public boolean isYoutubeVideo() {
            return site.toLowerCase(Locale.US).equals(Constant.SITE_YOUTUBE.toLowerCase(Locale.US));
        }


        protected MovieVideo(Parcel in) {
            videoId = in.readString();
            languageCode = in.readString();
            countryCode = in.readString();
            key = in.readString();
            name = in.readString();
            site = in.readString();
            size = in.readInt();
            type = in.readString();
        }

        public static final Creator<MovieVideo> CREATOR = new Creator<MovieVideo>() {
            @Override
            public MovieVideo createFromParcel(Parcel in) {
                return new MovieVideo(in);
            }

            @Override
            public MovieVideo[] newArray(int size) {
                return new MovieVideo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(videoId);
            parcel.writeString(languageCode);
            parcel.writeString(countryCode);
            parcel.writeString(key);
            parcel.writeString(name);
            parcel.writeString(site);
            parcel.writeInt(size);
            parcel.writeString(type);
        }
    }
}
