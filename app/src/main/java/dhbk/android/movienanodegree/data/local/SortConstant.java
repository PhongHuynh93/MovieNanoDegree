package dhbk.android.movienanodegree.data.local;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by phongdth.ky on 8/2/2016.
 * contains the stringdef of the 3 types of sort
 */
public abstract class SortConstant {
    @StringDef({MOST_POPULAR, HIGHEST_RATED, MOST_RATED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationMode {}

    public static final String MOST_POPULAR = "popularity.desc";
    public static final String HIGHEST_RATED = "vote_average.desc";
    public static final String MOST_RATED = "vote_count.desc";
}
