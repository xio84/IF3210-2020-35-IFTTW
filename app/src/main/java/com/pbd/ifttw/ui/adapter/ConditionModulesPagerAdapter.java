package com.pbd.ifttw.ui.adapter;

import android.content.Context;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.PlaceholderFragment;
import com.pbd.ifttw.ui.modules.SensorsModuleFragment;
import com.pbd.ifttw.ui.modules.TimerModuleFragment;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ConditionModulesPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.condition_module_name_1,
            R.string.condition_module_name_2,
            R.string.condition_module_name_3
    };
    private final Context mContext;

    public ConditionModulesPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        switch (position){
            //TODO: Replace with Condition Modules Fragment
            case 0:
                return new TimerModuleFragment();
            case 1:
                return new SensorsModuleFragment();
            default:
                return PlaceholderFragment.newInstance(position+1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}