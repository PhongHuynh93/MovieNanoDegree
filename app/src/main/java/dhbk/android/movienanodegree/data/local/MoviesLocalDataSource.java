package dhbk.android.movienanodegree.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

import dhbk.android.movienanodegree.data.MoviesDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by phongdth.ky on 8/2/2016.
 */
public class MoviesLocalDataSource implements MoviesDataSource {
    private final Context mContext;

    public MoviesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        mContext = context;
    }
}
