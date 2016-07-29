package dhbk.android.movienanodegree.io;

/**
 * Created by phongdth.ky on 7/15/2016.
 * Constants value used for search from spotify api
 *
 * @see <a href="https://developer.spotify.com/web-api/search-item/">Search for an Item
 * </a>
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
}
