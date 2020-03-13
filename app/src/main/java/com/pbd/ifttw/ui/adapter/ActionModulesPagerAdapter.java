package com.pbd.ifttw.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pbd.ifttw.R;
import com.pbd.ifttw.ui.main.PlaceholderFragment;
import com.pbd.ifttw.ui.modules.NotifyModuleFragment;
import com.pbd.ifttw.ui.modules.WiFiModuleFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ActionModulesPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.action_module_name_1,
            R.string.action_module_name_2,
            R.string.action_module_name_3
    };
    private final Context mContext;

    public ActionModulesPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
//        return PlaceholderFragment.newInstance(position + 1);
        switch (position) {
            //TODO: Replace with Action Modules Fragment
            case 0:
                return new NotifyModuleFragment();
            case 1:
                return new WiFiModuleFragment();
            default:
                return PlaceholderFragment.newInstance(position + 1);
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