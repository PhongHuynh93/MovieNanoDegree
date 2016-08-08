package dhbk.android.movienanodegree.dagger.app;

import android.content.Context;

import dhbk.android.movienanodegree.io.MovieInteractor;
import dhbk.android.movienanodegree.io.MovieRetrofitEndpoint;
import dhbk.android.movienanodegree.io.MovieRetrofitInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by phongdth.ky on 8/8/2016.
 * provide retrofit instance, retrofit endpoints, and interactor for communicating with server.
 */
@Module
public class MovieApiServiceModule {
//   retrofit instance
    @Provides
    @Singleton
    public Retrofit provideRetrofitInstance(Context context) {
        return MovieRetrofitInstance.getInstance(context);
    }

//    retrofit endpoint
    @Provides
    @Singleton
    public MovieRetrofitEndpoint provideSpotifyApiService(Retrofit retrofit) {
        return retrofit.create(MovieRetrofitEndpoint.class);
    }

//    interact with server
    @Provides
    @Singleton
    public MovieInteractor provideArtistSearchInteractor(MovieRetrofitEndpoint apiService, Context context) {
        return new MovieInteractor(context, apiService);
    }
}
