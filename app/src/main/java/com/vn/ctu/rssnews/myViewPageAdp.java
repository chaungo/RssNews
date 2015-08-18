package com.vn.ctu.rssnews;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class myViewPageAdp extends FragmentPagerAdapter {

    public myViewPageAdp(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tab1();
            case 1:
                return new tab2();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 2;
    }




}
