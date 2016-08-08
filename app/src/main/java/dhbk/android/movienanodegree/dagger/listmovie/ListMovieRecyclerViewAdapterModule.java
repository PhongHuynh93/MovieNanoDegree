package dhbk.android.movienanodegree.dagger.listmovie;

import android.content.Context;

import dhbk.android.movienanodegree.ui.listmovie.ListMovieRecyclerViewAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by huynhducthanhphong on 8/1/16.
 */
@Module
public class ListMovieRecyclerViewAdapterModule {
    // return adapter for this view, context from parent component
    @Provides
    public ListMovieRecyclerViewAdapter provideAdapter(Context context) {
        return new ListMovieRecyclerViewAdapter(context, null);
    }
}
