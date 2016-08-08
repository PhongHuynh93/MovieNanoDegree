package dhbk.android.movienanodegree.io;

/**
 * Created by phongdth.ky on 8/8/2016.
 * define the endpoints.
 */

import dhbk.android.movienanodegree.models.DiscoverMovieResponse;
import dhbk.android.movienanodegree.models.MovieReviewsResponse;
import dhbk.android.movienanodegree.models.MovieVideosResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * All methods that will make a request to Spotify API
 * http://docs.themoviedb.apiary.io/#reference/movies/movieidvideos/get
 */
public interface MovieRetrofitEndpoint {
    /**
     *     Get the videos (trailers, teasers, clips, etc...) for a specific movie id to {@link MovieVideosResponse}
     * http://api.themoviedb.org/3/movie/id/videos
     */
    @GET("movie/{id}/videos")
    Observable<MovieVideosResponse> getMovieVideos(@Path("id") long movieId);

    /**
     *     Get the reviews for a particular movie id to {@link MovieReviewsResponse}
     * http://api.themoviedb.org/3/movie/id/reviews
     */
    @GET("movie/{id}/reviews")
    Observable<MovieReviewsResponse> getMovieReviews(@Path("id") long movieId);


    /**
     * http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
     * @param sortBy type of sort of movies
     * @param page the pages of movies that you want to look
     * @return
     */
    @GET("discover/movie")
    Observable<DiscoverMovieResponse> discoverMovies(@Query("sort_by") String sortBy, @Query("page") Integer page);
}
