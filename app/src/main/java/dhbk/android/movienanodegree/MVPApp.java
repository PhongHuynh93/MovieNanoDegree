package dhbk.android.movienanodegree;

import android.app.Application;

import dhbk.android.movienanodegree.dagger.app.ApplicationModule;
import dhbk.android.movienanodegree.dagger.app.DaggerMovieComponent;
import dhbk.android.movienanodegree.dagger.app.MovieApiServiceModule;
import dhbk.android.movienanodegree.dagger.app.MovieComponent;
import dhbk.android.movienanodegree.dagger.app.RepositionModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by phongdth.ky on 7/13/2016.
 *  * this is a singleton application, which create graph for dagger 2 and setup font for all app
 */
public class MVPApp extends Application {
    private MovieComponent mMovieComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFont();
        setupGraph();
    }

    // setup parent component
    private void setupGraph() {
        mMovieComponent = DaggerMovieComponent
                .builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .repositionModule(new RepositionModule())
                .movieApiServiceModule(new MovieApiServiceModule())
                .build();
    }

    // setup custome default font for all views in projects.
    private void setupFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public MovieComponent getMovieComponent() {
        return mMovieComponent;
    }
}
