package dhbk.android.movienanodegree.dagger.detailmovie;

/**
 * Created by phongdth.ky on 7/29/2016.
 */

import dagger.Module;
import dagger.Provides;
import dhbk.android.movienanodegree.ui.detailmovie.DetailMovieContract;
import dhbk.android.movienanodegree.ui.listmovie.presenter.ListMoviePresenter;
import dhbk.android.movienanodegree.util.ActivityScoped;

/**
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ListMoviePresenter}.
 * provide views
 */
@Module
public class DetailMoviePresenterModule {
    private final DetailMovieContract.View mView;

    public DetailMoviePresenterModule(DetailMovieContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScoped
    DetailMovieContract.View provideDetailMovieContractView() {
        return mView;
    }

}
