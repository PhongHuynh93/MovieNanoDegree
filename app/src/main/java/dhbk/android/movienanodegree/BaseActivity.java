package dhbk.android.movienanodegree;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by huynhducthanhphong on 7/28/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        injectViews();
        injectDependencies();
        if (hasToolbar()) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.toolbar_open_drawer);
            ab.setDisplayHomeAsUpEnabled(true); // set the left arrow in toolbar
        }

        initView();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (hasUseCustomeFont()) {
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }


    //     Every object annotated with {@link butterknife.Bind} its gonna injected trough butterknife
    private void injectViews() {
        ButterKnife.bind(this);
    }

    /**
     * Setup the object graph and inject the dependencies needed on this activity.
     */
    protected abstract void injectDependencies();

    // if a activity want to use the custome font, return true
    protected abstract boolean hasUseCustomeFont();

    // return layout for activity
    @LayoutRes
    public abstract int getLayout();

    // check a view has toolbar or not
    protected abstract boolean hasToolbar();

    // init view object in view
    protected abstract void initView();

}
