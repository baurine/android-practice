package com.baurine.embedtabbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabContentPagerAdapter mPagerAdapter;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        setupViewPager();
        setupActionBar();
    }

    private void setupViewPager() {
        mPagerAdapter = new TabContentPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mActionBar.setSelectedNavigationItem(position);
            }
        });
    }

    private void setupActionBar() {
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener listener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        for (int i = 0; i < 3; i++) {
            mActionBar.addTab(mActionBar.newTab()
                    .setText("Tab " + (i + 1))
                    .setTabListener(listener));
        }
    }

    public static class TabContentPagerAdapter extends FragmentStatePagerAdapter {

        public TabContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new MainActivityFragment();
            Bundle arg = new Bundle();
            switch (position) {
                case 0:
                    arg.putInt(MainActivityFragment.ARG_KEY, Color.RED);
                    break;
                case 1:
                    arg.putInt(MainActivityFragment.ARG_KEY, Color.YELLOW);
                    break;
                case 2:
                    arg.putInt(MainActivityFragment.ARG_KEY, Color.BLUE);
                    break;
            }
            fragment.setArguments(arg);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
