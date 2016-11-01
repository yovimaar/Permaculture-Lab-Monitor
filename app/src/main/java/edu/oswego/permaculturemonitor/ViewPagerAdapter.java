package edu.oswego.permaculturemonitor;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;


/**
 * Created by Yovi on 10/31/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Graphs", "Maps", "Settings"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                FragmentTab1 fragmenttab1 = new FragmentTab1();
                return fragmenttab1;

            // Open FragmentTab2.java
            case 1:
                FragmentTab2 fragmenttab2 = new FragmentTab2();
                return fragmenttab2;

            // Open FragmentTab3.java
            case 2:
                FragmentTab3 fragmenttab3 = new FragmentTab3();
                return fragmenttab3;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}