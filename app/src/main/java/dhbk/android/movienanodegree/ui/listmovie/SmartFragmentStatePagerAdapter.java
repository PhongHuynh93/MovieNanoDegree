package dhbk.android.movienanodegree.ui.listmovie;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by phongdth.ky on 7/29/2016.FragmentStatePagerAdapter
 * FragmentStatePagerAdapter:
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

    /**
     * instantiateItem is called when the viewPager calls the getItem method.
     * When the viewPager is first displayed, getItem is called for the currently displayed tab and the following tab.
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}