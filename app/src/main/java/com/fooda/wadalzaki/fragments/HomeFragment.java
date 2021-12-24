package com.fooda.wadalzaki.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.adapters.ViewPagerAdapter;
import com.fooda.wadalzaki.themes.SetThemes;
import com.fooda.wadalzaki.ui.SearchActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;


public class HomeFragment extends Fragment  {

    public TabLayout tabLayout;
    //Fragments
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
   // ImageButton textView;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("CutPasteId")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Title Bar
       // title_layout = (RelativeLayout) view.findViewById(R.id.title_layout);
//        textView=  view.findViewById(R.id.search_imb_title);
//        textView.setOnClickListener(this);
//        search_imb_title.setOnClickListener(this);
        //TabLayout
        tabLayout =  view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        viewPagerAdapter= new ViewPagerAdapter(getFragmentManager(),getContext());

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        createTabIcons();
    }
    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText("الهاتف");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_call, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("المفضلة");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_favorite, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText("الاسماء");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_person, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        SetThemes.ChangeTabColor(tabLayout,getContext());
    }
}