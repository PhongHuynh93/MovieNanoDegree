package dhbk.android.movienanodegree;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by huynhducthanhphong on 7/16/16.
 */
public abstract class BaseFragment extends Fragment {
    // inject view and dependance
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayout(), container, false);
        injectViews(v);
        injectDependencies();
        if (hasToolbar()) {
            setHasOptionsMenu(true);
        }
        initView();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doThingWhenCreateApp();
    }


    @Override
    public void onResume() {
        super.onResume();
        doThingWhenResumeApp();
    }


    @Override
    public void onPause() {
        doThingWhenPauseApp();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        doThingWhenDestroyApp();
        super.onDestroy();
    }

    // get the data from intent
    protected abstract void doThingWhenCreateApp();


    // when app is on resume, add listener, start the presenter
    protected abstract void doThingWhenResumeApp();

    // when app is on pause state, do something to release resources.
    protected abstract void doThingWhenPauseApp();

    // when app is on destroy state, stop network.
    protected abstract void doThingWhenDestroyApp();

    // return layout for fragment
    @LayoutRes
    public abstract int getLayout();

    // check a view has toolbar or not
    protected abstract boolean hasToolbar();

    // init view object in view
    protected abstract void initView();

    /**
     * Setup the object graph and inject the dependencies needed on this fragment.
     */
    protected abstract void injectDependencies();

    // Every object annotated with {@link butterknife.Bind} its gonna injected trough butterknife
    private void injectViews(View v) {
        ButterKnife.bind(this, v);
    }


}
