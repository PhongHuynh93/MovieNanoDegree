package dhbk.android.movienanodegree.ui.home.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.home.adapter.ListMovieRecyclerViewAdapter;

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
