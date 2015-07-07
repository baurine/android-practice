package com.baurine.designsupportlibdemo;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/*
参考资料：
http://inthecheesefactory.com/blog/android-design-support-library-codelab/en
http://blog.csdn.net/eclipsexys/article/details/46349721
https://medium.com/ribot-labs/exploring-the-new-android-design-support-library-b7cda56d2c32
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ViewGroup mLayoutContentRoot;
    private FloatingActionButton mFabBtn;
    private Toolbar mToolbar;
    //    private TabLayout mTabLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setupFabBtn();
        setupToolbar();
        setupDrawerLayout();
        setupNavigationView();
//        setupTabLayout();
        setupCollapsingToolbarLayout();
    }

    private void setupCollapsingToolbarLayout() {
        mCollapsingToolbarLayout.setTitle("Design Library");
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mLayoutContentRoot = (ViewGroup) findViewById(R.id.layout_content_root);
        mFabBtn = (FloatingActionButton) findViewById(R.id.fab_btn);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
    }

    private void setupFabBtn() {
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mLayoutContentRoot, "Hello, I'm Snackbar!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("Toolbar");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        toggle.syncState();
        mDrawerLayout.setDrawerListener(toggle);
    }

    private void setupNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_dashboard:
                                break;
                            case R.id.nav_event:
                                break;
                            case R.id.nav_forum:
                                break;
                            case R.id.nav_headset:
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
    }

    /*
    private void setupTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab 3"));
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
