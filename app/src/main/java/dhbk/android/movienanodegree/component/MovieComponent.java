package dhbk.android.movienanodegree.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import dhbk.android.movienanodegree.module.ApplicationModule;
import dhbk.android.movienanodegree.module.MovieModule;
import dhbk.android.movienanodegree.module.TasksRepositoryModule;

/**
 * Created by phongdth.ky on 7/29/2016.
 * parent component
 * type: Component Dependencies
 */
@Singleton
@Component(modules = {ApplicationModule.class, TasksRepositoryModule.class, MovieModule.class})
public interface MovieComponent {
    // TODO: 7/29/2016  declare components that child component need here
    Context getContext();
}
