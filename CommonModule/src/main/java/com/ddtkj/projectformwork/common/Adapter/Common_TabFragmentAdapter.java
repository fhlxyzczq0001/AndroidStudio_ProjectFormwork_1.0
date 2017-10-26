package com.ddtkj.projectformwork.common.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator 杨重诚
 * @date: 2016/11/8:10:25
 */

public class Common_TabFragmentAdapter extends FragmentPagerAdapter{
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mTransaction = null;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>(2);
    String[] TITLE;

    public Common_TabFragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList, String[] title) {
        super(fm);
        mFragmentManager = fm;
        this.mFragmentList = mFragmentList;
        this.TITLE = title;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        String name = getTag(position);
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mTransaction.attach(fragment);
        }else {
        fragment = getItem(position);
        mTransaction.add(container.getId(), fragment, getTag(position));
		 }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mTransaction == null) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        mTransaction.detach((Fragment) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {

        if (mTransaction != null) {
            mTransaction.commitAllowingStateLoss();
            mTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }

    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    protected String getTag(int position) {
        return TITLE[position];
    }

}
