package dhbk.android.movienanodegree.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import dhbk.android.movienanodegree.MVPApp;
import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.home.module.ListMoviePresenterModule;

/**
 * Created by huynhducthanhphong on 7/28/16.
 */
public class ListMovieAdapter extends SmartFragmentStatePagerAdapter {
    private static final int NUM_ITEMS = 3;
    private static final int MOST_POPULAR = 0;
    private static final int HIGHEST_RATED = 1;
    private static final int MOST_RATED = 2;
    private final Context mContext;

    public ListMovieAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * getItem will be called whenever the adapter needs a fragment and the fragment does not exist.
     * -> khi đã tạo rồi thì ko có gọi nữa
     * in this method: create three view with three presenter
     * @param position
     * @return
     * http://stackoverflow.com/questions/19339500/when-is-fragmentpageradapters-getitem-called
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MOST_POPULAR: // Fragment # 0 - This will show FirstFragment
                Fragment fragment1 = ListMovieFragment.newInstance(MOST_POPULAR);
                // create the presenter
                DaggerListMoviePresenterComponent
                        .builder()
                        .movieComponent(((MVPApp) mContext).getMovieComponent())
                        .listMoviePresenterModule(new ListMoviePresenterModule((ListMovieContract.View)fragment1))
                        .build()
                        .inject(this);
                return fragment1;

            case HIGHEST_RATED: // Fragment # 0 - This will show FirstFragment different title
                Fragment fragment2 =  ListMovieFragment.newInstance(HIGHEST_RATED);
                DaggerListMoviePresenterComponent
                        .builder()
                        .movieComponent(((MVPApp) mContext).getMovieComponent())
                        .listMoviePresenterModule(new ListMoviePresenterModule((ListMovieContract.View)fragment2))
                        .build()
                        .inject(this);
                return fragment2;
            case MOST_RATED: // Fragment # 1 - This will show SecondFragment
                Fragment fragment3 =   ListMovieFragment.newInstance(MOST_RATED);
                DaggerListMoviePresenterComponent
                        .builder()
                        .movieComponent(((MVPApp) mContext).getMovieComponent())
                        .listMoviePresenterModule(new ListMoviePresenterModule((ListMovieContract.View)fragment3))
                        .build()
                        .inject(this);
                return fragment3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case MOST_POPULAR:
                return mContext.getResources().getString(R.string.home_adapter_pager_most_popular);
            case HIGHEST_RATED:
                return mContext.getResources().getString(R.string.home_adapter_pager_highest_rated);
            case MOST_RATED:
                return mContext.getResources().getString(R.string.home_adapter_pager_most_rated);
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
