package dhbk.android.movienanodegree;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by phongdth.ky on 7/13/2016.
 */
public class MVPApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupFont();
//        setupGraph();
    }

//    // contains dependency use to inject into class
//    private void setupGraph() {
//        // we use subcomponent, this is a parent dependance
//        mSpotifyStreamerComponent = DaggerSpotifyStreamerComponent
//                .builder()
//                .spotifyStreamerModule(new SpotifyStreamerModule(this))
//                .build();
//    }

    // setup custome font for all my views
    private void setupFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
//
//    public SpotifyStreamerComponent getSpotifyStreamerComponent() {
//        return mSpotifyStreamerComponent;
//    }
}
