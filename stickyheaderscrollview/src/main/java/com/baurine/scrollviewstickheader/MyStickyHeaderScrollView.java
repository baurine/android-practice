package com.baurine.scrollviewstickheader;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class MyStickyHeaderScrollView extends FrameLayout
        implements MyScrollView.OnScrollListener {

    private static final String TAG = MyStickyHeaderScrollView.class.getSimpleName();

    private ViewGroup mContentView;
    private ViewGroup mStickyHeaderViewContainer;
    private View mStickyHeaderViewContent;
    private int mStickyHeaderViewTop;

    public MyStickyHeaderScrollView(Context context) {
        super(context);

        init();
    }

    public MyStickyHeaderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyStickyHeaderScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                doInit();
            }
        });
    }

    private void doInit() {
        mContentView = (ViewGroup) getChildAt(0);
        removeAllViews();

        MyScrollView myScrollView = new MyScrollView(getContext());
        myScrollView.setOnScrollListener(this);
        myScrollView.addView(mContentView);
        addView(myScrollView);
    }

    public void setStickyHeaderView(final int resId) {
        post(new Runnable() {
            @Override
            public void run() {
                doSetStickyHeaderView(resId);
            }
        });
    }

    private void doSetStickyHeaderView(int resId) {
        mStickyHeaderViewContainer = (ViewGroup) mContentView.findViewById(resId);
        mStickyHeaderViewContent = mStickyHeaderViewContainer.getChildAt(0);

        int height = mStickyHeaderViewContent.getMeasuredHeight();
        ViewGroup.LayoutParams params = mStickyHeaderViewContainer.getLayoutParams();
        params.height = height;
        mStickyHeaderViewContainer.setLayoutParams(params);
        mStickyHeaderViewTop = mStickyHeaderViewContainer.getTop();

        Log.i(TAG, String.format("top: %d", mStickyHeaderViewTop));
    }


    @Override
    public void onScroll(final int y) {
        Log.i(TAG, String.format("scroll y --> %d", y));

        post(new Runnable() {
            @Override
            public void run() {
                doScroll(y);
            }
        });
    }

    private void doScroll(int y) {
        if (mStickyHeaderViewContainer == null) {
            return;
        }

        if (y > mStickyHeaderViewTop &&
                mStickyHeaderViewContent.getParent() == mStickyHeaderViewContainer) {
            mStickyHeaderViewContainer.removeView(mStickyHeaderViewContent);
            addView(mStickyHeaderViewContent);
        } else if (y < mStickyHeaderViewTop &&
                mStickyHeaderViewContent.getParent() == MyStickyHeaderScrollView.this) {
            removeView(mStickyHeaderViewContent);
            mStickyHeaderViewContainer.addView(mStickyHeaderViewContent);
        }
    }
}
