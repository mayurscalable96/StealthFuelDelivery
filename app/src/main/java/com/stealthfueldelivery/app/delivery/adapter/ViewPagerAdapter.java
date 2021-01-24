package com.stealthfueldelivery.app.delivery.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
       /* switch (position) {
            case 0:
                return new NewOrderAssigned();
            case 1:
                return new AssignedOrderDelivered();
            default:
                return null; // Problem occurs at this condition!
        }*/
        return mFragmentList.get(position);
    }
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
       /* if (position == 0)
        {
            title = "New Order Assigned";
        }
        else if (position == 1)
        {
            title = "Assigned Order Delivered";
        }*/
        return mFragmentTitleList.get(position);
    }
}
