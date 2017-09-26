package com.mssd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mssd.data.ShitangBean;

import java.util.List;

/**
 * Created by DELL on 2017/9/12.
 */

public class TestAdapter extends FragmentPagerAdapter {
    private List<String> list;
    private List<Fragment> list1;

    public TestAdapter(FragmentManager fm, List<String> list, List<Fragment> list1) {
        super(fm);
        this.list = list;
        this.list1 = list1;
    }

    @Override
    public Fragment getItem(int position) {
        return list1.get(position);
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
