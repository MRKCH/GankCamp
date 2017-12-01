package com.cooke.gankcamp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cooke.gankcamp.ui.fragment.ImageFragment;
import com.cooke.gankcamp.ui.fragment.NewsFragment;

import java.util.ArrayList;

/**
 * Created by kch on 2017/11/27.
 */

public class GankFragmentAdapter extends FragmentPagerAdapter {

    public GankFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewsFragment();
            case 1:
                return new ImageFragment();
            default:
                break;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "热门";
            case 1:
                return "萌妹子";
            default:
                break;
        }
        return "";
    }
}
