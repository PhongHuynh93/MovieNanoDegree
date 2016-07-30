package dhbk.android.movienanodegree.io;

import dhbk.android.spotifygcs.io.model.ArtistSearchResponse;
import dhbk.android.spotifygcs.io.model.TopTrackSearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by phongdth.ky on 7/15/2016.
 * All methods that will make a request to Spotify API
 * http://docs.themoviedb.apiary.io/#reference/movies/movieidvideos/get
 */
public interface MovieApiService {
    /**
     *     Get the videos (trailers, teasers, clips, etc...) for a specific movie id.
     * http://api.themoviedb.org/3/movie/id/videos
      */
    @GET("movie/{id}/videos")
    Observable<MovieVideosResponse> getMovieVideos(@Path("id") long movieId);

    /**
     *     Get the reviews for a particular movie id.
     * http://api.themoviedb.org/3/movie/id/reviews
     */
    @GET("movie/{id}/reviews")
    Observable<MovieReviewsResponse> getMovieReviews(@Path("id") long movieId);

}
