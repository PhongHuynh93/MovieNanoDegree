package dhbk.android.movienanodegree.ui.home.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import dhbk.android.movienanodegree.R;
import dhbk.android.movienanodegree.ui.home.ListMovieItemFragment;

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
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case MOST_POPULAR: // ListMovieItemFragment # 0 - This will show FirstFragment
                return ListMovieItemFragment.newInstance(MOST_POPULAR);
            case HIGHEST_RATED: // ListMovieItemFragment # 0 - This will show FirstFragment different title
                return ListMovieItemFragment.newInstance(HIGHEST_RATED);
            case MOST_RATED: // ListMovieItemFragment # 1 - This will show SecondFragment
                return ListMovieItemFragment.newInstance(MOST_RATED);
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
}
