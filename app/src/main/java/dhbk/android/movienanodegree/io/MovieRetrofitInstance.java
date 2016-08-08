package dhbk.android.movienanodegree.io;

import android.content.Context;
import android.support.annotation.NonNull;

import dhbk.android.movienanodegree.util.Constant;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 7/15/2016.
 * This class contains the instance of the rest adapter to be connected with Movie Api service
 * Tutorial retrofit
 * @see <a href="https://gist.github.com/PhongHuynh93/d83a59df16e1ba8ad5bc9c61f6865631">Retrofit Tutorial</a>
 */
public class MovieRetrofitInstance {
    private static Retrofit sRetrofit;

    public static Retrofit getInstance(@NonNull Context context){
        checkNotNull(context);
        // because we want this api use rxjava in callback so we attach the adapter to this retrofit instance.
        if(sRetrofit == null)
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getClient(context))
                    .build();

        return sRetrofit;
    }


    /**
     * HttpLogging Inerceptr use to debug network
     * https://github.com/codepath/android_guides/wiki/Using-OkHttp
     * print HTTP requests/responses through LogCat
     * Timeouts: https://github.com/square/okhttp/wiki/Recipes
     * Use timeouts to fail a call when its peer is unreachable
     * Handling authentication:
     * OkHttp can automatically retry unauthenticated requests. When a response is 401 Not Authorized, an Authenticator is asked to supply credentials.
     *
     * Cache: add cache to client https://github.com/codepath/android_guides/wiki/Using-OkHttp
     */
    private static OkHttpClient getClient(Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // add loggingInterceptor to client
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Constant.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new AuthorizationInterceptor());

        // add cache to client
        final File baseDir = context.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, Constant.CACHE_DIR);
            builder.cache(new Cache(cacheDir, Constant.CACHE_SIZE));
        }

        // build all client
        return builder.build();
    }
}
