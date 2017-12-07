package com.cooke.gankcamp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cooke.gankcamp.contsants.ContentType;
import com.cooke.gankcamp.ui.fragment.SortGankFragment;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankFragAdapter extends FragmentPagerAdapter {

    public SortGankFragAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        String contentType = (String) getPageTitle(position);

        return SortGankFragment.getInstance(contentType);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String contentType = "";
        switch (position){
            case 0:
                contentType = ContentType.ALL;
                break;
            case 1:
                contentType = ContentType.ANDROID;
                break;
            case 2:
                contentType = ContentType.IOS;
                break;
            case 3:
                contentType = ContentType.WEB;
                break;
            case 4:
                contentType = ContentType.RECOMMEND;
                break;
            case 5:
                contentType = ContentType.EXTENTAL_RESOURCE;
                break;

        }
        return contentType;
    }
}
