package dhbk.android.movienanodegree.util;

import android.content.Context;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import dhbk.android.movienanodegree.io.MovieRetrofitInstance;
import dhbk.android.movienanodegree.ui.listmovie.ListMovieRecyclerViewAdapter;

/**
 * Created by phongdth.ky on 8/8/2016.
 */
public class Constant {
    /**
     * retrofit instance
     * {@link MovieRetrofitInstance#getInstance(Context)}
     */
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    //    cache okttp
    public static final String CACHE_DIR = "HttpResponseCache";
    public static final long CACHE_SIZE = 10 * 1024 * 1024;    // 10 MB
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 60;
    public static final int TIMEOUT = 60;
    //    api key
    public static final String THE_MOVIE_DB_API_KEY = "53470b56a60668274e1dd9f84d882564";

    //    video
    public static final String MOVIE_RESULTS = "results";
    public static final String MOVIE_ID = "id";

    // movie discovers
    // http://docs.themoviedb.apiary.io/#reference/discover/discovermovie/get
    public static final String TITLE = "title";
    public static final String VOTE = "vote_average";

    // sort constant
    @StringDef({MOST_POPULAR, HIGHEST_RATED, MOST_RATED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {}

    public static final String MOST_POPULAR = "popularity.desc";
    public static final String HIGHEST_RATED = "vote_average.desc";
    public static final String MOST_RATED = "vote_count.desc";

//    loader for load db to populate datas
    public static final int LOADER_ID = 0;

//    list movie
    public static final String BROADCAST_CREATE_VIEWPAGER_ITEM_FRAG = "create_view_pager_item_frag";


    /**
     * url for load image in {@link ListMovieRecyclerViewAdapter}
     */
    public static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String POSTER_IMAGE_SIZE = "w780";

    public static final String MOVIE_VIDEOS_KEY = "MovieVideos";
    public static final String MOVIE_REVIEWS_KEY = "MovieReviews";

    public static final double VOTE_PERFECT = 9.0;
    public static final double VOTE_GOOD = 7.0;
    public static final double VOTE_NORMAL = 5.0;

    public static final String SITE_YOUTUBE = "YouTube";
    public static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";

}
