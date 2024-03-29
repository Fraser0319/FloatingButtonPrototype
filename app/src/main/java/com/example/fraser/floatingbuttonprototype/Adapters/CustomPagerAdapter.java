package com.example.fraser.floatingbuttonprototype.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * class based on tutorial:
 * method based on tutorial : http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fragManager) {
        super(fragManager);
    }

    @Override
    public Fragment getItem(int pos) {

        Fragment fragment = fragmentList.get(pos);
        return fragment;
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int pos){
        return titleList.get(pos);
    }


}
