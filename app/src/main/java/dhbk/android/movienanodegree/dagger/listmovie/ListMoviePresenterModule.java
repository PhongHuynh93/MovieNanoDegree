package dhbk.android.movienanodegree.dagger.listmovie;

/**
 * Created by phongdth.ky on 7/29/2016.
 */

import dhbk.android.movienanodegree.ui.listmovie.ListMovieContract;
import dhbk.android.movienanodegree.util.ActivityScoped;

import dagger.Module;
import dagger.Provides;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link dhbk.android.movienanodegree.ui.listmovie.ListMoviePresenter}.
 * provide views
 */
@Module
public class ListMoviePresenterModule {
    private final ListMovieContract.View mView;

    public ListMoviePresenterModule(ListMovieContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScoped
    ListMovieContract.View provideTasksContractView() {
        return mView;
    }

}
