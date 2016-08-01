package dhbk.android.movienanodegree.ui.home.module;

/**
 * Created by phongdth.ky on 7/29/2016.
 */

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.home.ListMovieContract;
import dhbk.android.movienanodegree.ui.home.ListMoviePresenter;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ListMoviePresenter}.
 */
@Module
public class ListMoviePresenterModule {
    private final ListMovieContract.View mView;

    public ListMoviePresenterModule(ListMovieContract.View view) {
        mView = view;
    }

    @Provides
    ListMovieContract.View provideTasksContractView() {
        return mView;
    }

//    @Provides
//    MovieInteractor provideMovieInteractor(MovieInteractor movieInteractor) {
//        return movieInteractor;
//    }

}
