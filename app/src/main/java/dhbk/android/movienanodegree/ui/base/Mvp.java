package dhbk.android.movienanodegree.ui.base;

/**
 * Created by huynhducthanhphong on 7/31/16.
 * rule of DRY (Dont Repeat Yourself )
 * so this class contains method that every views, presenters will use.
 *
 * In our software a piece of knowledge should be in one single location.
 *
 */
public interface Mvp {
    public interface BaseView<T> {
        void setPresenter(T presenter);
    }

    public interface BasePresenter {
        void start();
    }
}
