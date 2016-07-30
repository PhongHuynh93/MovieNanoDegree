package dhbk.android.movienanodegree.io;

/**
 * Created by phongdth.ky on 7/15/2016.
 * Constants value used for search from movie api
 *
 */
class MovieRequestConstants {
    // base url for movie search
    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    //    cache okttp
    public static final String CACHE_DIR = "HttpResponseCache";
    public static final long CACHE_SIZE = 10 * 1024 * 1024;    // 10 MB
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 60;
    public static final int TIMEOUT = 60;


    //    api key
    public static final String THE_MOVIE_DB_API_KEY = "53470b56a60668274e1dd9f84d882564";

}
