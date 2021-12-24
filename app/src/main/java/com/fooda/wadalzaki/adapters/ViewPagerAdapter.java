package com.fooda.wadalzaki.adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.fooda.wadalzaki.fragments.CallFragment;
import com.fooda.wadalzaki.fragments.FavoriteFragment;
import com.fooda.wadalzaki.fragments.PersonFragment;


/**
 * Main ViewPageAdapter，AndTablayout配套使用
 * Created by leosunzh on 2015/12/12.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    final int PAGE_COUNT =3;
    private String titles[]={"الهاتف","المفضلة","الاسماء"} ;
//    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
//        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return PersonFragment.newInstance(position);

            case 1:
                return FavoriteFragment.newInstance(position);

            case 2:
                return CallFragment.newInstance(position);

        }
        return  CallFragment.newInstance(position);

    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}