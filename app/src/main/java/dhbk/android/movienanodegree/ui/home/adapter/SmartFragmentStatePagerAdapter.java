package dhbk.android.movienanodegree.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import android.view.ViewGroup;

import dhbk.android.movienanodegree.ui.Constant;
import hugo.weaving.DebugLog;

/**
 * Created by phongdth.ky on 7/29/2016.
 */
public abstract class SmartFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private final Context mContext;
    // Sparse array to keep track of registered fragments in memory
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private boolean mFirst = true; // send broadcast that the fragment has create for the first time

    public SmartFragmentStatePagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    // Register the fragment when the item is instantiated

    /**
     * instantiateItem is called when the viewPager calls the getItem method.
     * When the viewPager is first displayed, getItem is called for the currently displayed tab and the following tab.
     * @param container
     * @param position
     * @return
     */
    @DebugLog
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        // sent intent
        if (mFirst) {
            mFirst = false;
            Intent intent = new Intent(Constant.BROADCAST_CREATE_VIEWPAGER_ITEM_FRAG);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @DebugLog
    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}