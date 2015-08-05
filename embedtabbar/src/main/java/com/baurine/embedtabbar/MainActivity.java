package com.baurine.embedtabbar;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Method;

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
        // 不显示图标和标题
        mActionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

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

        TypedArray iconsId = getResources().obtainTypedArray(R.array.action_tab_icons);
        for (int i = 0; i < 3; i++) {
            View view = getLayoutInflater().inflate(R.layout.action_tab, null);
            ImageView imgView = (ImageView) view.findViewById(R.id.icon);
            imgView.setImageResource(iconsId.getResourceId(i, -1));

            mActionBar.addTab(mActionBar.newTab()
                    // .setText("Tab " + (i + 1))
                    .setCustomView(view)
                    .setTabListener(listener));
        }

        enableEmbededTabs(mActionBar);
    }

    private void enableEmbededTabs(Object actionBar) {
        try {
            Method setHasEmbeddedTabsMethod = actionBar.getClass()
                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, true);
        } catch (Exception e) {
            e.printStackTrace();
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
