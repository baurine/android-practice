package com.baurine.toolbardrawerlayoutdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/*
教程来源：http://chenqichao.me/2014/12/08/108-Android-Toolbar-DrawerLayout-01/
 */

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.tl_custom)
    Toolbar mToolbar;
    @InjectView(R.id.dl_custom)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.lv_drawer_menu)
    ListView mLvDrawerMenu;

    private static final String[] mMenuItems = {
            "List Item 01",
            "List Item 02",
            "List Item 03",
            "List Item 04",
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setupToolbar();
        setupDrawerLayout();
        setupDrawerMenu();
    }

    private void setupToolbar() {
        mToolbar.setTitle("Toolbar");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle drawerToggle =
                new ActionBarDrawerToggle(this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.drawer_open,
                        R.string.drawer_close);
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    private void setupDrawerMenu() {
        ArrayAdapter arrayAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, mMenuItems);
        mLvDrawerMenu.setAdapter(arrayAdapter);
    }
}
