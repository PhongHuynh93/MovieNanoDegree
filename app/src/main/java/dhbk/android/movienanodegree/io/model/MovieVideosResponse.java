package dhbk.android.movienanodegree.io.model;

/**
 * Created by huynhducthanhphong on 7/30/16.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
    @SerializedName(MovieResponseConstant.MOVIE_RESULTS)
    private ArrayList<MovieVideo> results;

}
