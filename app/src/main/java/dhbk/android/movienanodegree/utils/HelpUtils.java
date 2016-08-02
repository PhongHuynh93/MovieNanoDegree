package dhbk.android.movienanodegree.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by huynhducthanhphong on 8/2/16.
 */
public class HelpUtils {
    public static float getPixelForDp(Context context, float dp) {
        /// Converts dp dip into its equivalent px
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}
