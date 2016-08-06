package dhbk.android.movienanodegree.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.interactor.MovieInteractor;
import dhbk.android.movienanodegree.io.MovieApiAdapter;
import dhbk.android.movienanodegree.io.MovieApiService;
import retrofit2.Retrofit;

/**
 * Created by phongdth.ky on 7/29/2016.
 * // TODO: 7/29/2016 add movie module here
 */
@Module
public class MovieModule {
    /**
     * retrofit instance
     * @return
     */
    @Provides
    public Retrofit provideRetrofitInstance(Context context) {
        return MovieApiAdapter.getInstance(context);
    }

    /**
     * endpoints
     * @param retrofit
     * @return
     */
    @Provides
    public MovieApiService provideSpotifyApiService(Retrofit retrofit) {
        return retrofit.create(MovieApiService.class);
    }

    /**
     * method for client to connect to api
     * @param apiService
     * @return
     */
    @Provides
    public MovieInteractor provideArtistSearchInteractor(MovieApiService apiService, Context context) {
        return new MovieInteractor(context, apiService);
    }
}
