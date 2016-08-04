package dhbk.android.movienanodegree;

import android.app.Application;

import dhbk.android.movienanodegree.component.DaggerMovieComponent;
import dhbk.android.movienanodegree.component.MovieComponent;
import dhbk.android.movienanodegree.data.MovieRepositionModule;
import dhbk.android.movienanodegree.module.ApplicationModule;
import dhbk.android.movienanodegree.module.MovieModule;
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
                .movieRepositionModule(new MovieRepositionModule())
                .movieModule(new MovieModule())
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
