package com.baurine.customeview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class StickyNavLayoutTestActivity extends AppCompatActivity {

    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    private FragmentPagerAdapter adapter;
    private TabFragment[] fragments = new TabFragment[mTitles.length];

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stickynavlayout_test);

        initViews();
        initDatas();
        initEvents();
    }

    private void initViews() {
        tabLayout = (TabLayout) findViewById(R.id.id_stickynavlayout_indicator);
        viewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
    }

    private void initDatas() {
        for (int i = 0; i < mTitles.length; i++) {
            fragments[i] = TabFragment.newInstance(mTitles[i]);
        }

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initEvents() {

    }
}
