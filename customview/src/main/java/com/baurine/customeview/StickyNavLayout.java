package com.baurine.customeview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

public class StickyNavLayout extends LinearLayout {

    private View topView;
    private View navView;
    private ViewPager viewPager;

    private OverScroller overScroller;
    private VelocityTracker velocityTracker;
    private int touchSlop;
    private int maxVelocity, minVelocity;

    private float lastY;
    private boolean dragging;

    private int topViewHeight;
    private boolean topViewHide;
    private ScrollView innerScrollView;

    public StickyNavLayout(Context context) {
        this(context, null);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        overScroller = new OverScroller(context);
        velocityTracker = VelocityTracker.obtain();
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public StickyNavLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        topView = findViewById(R.id.id_stickynavlayout_topview);
        navView = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException("id_stickynavlayout_viewpager should be ViewPager");
        }
        viewPager = (ViewPager) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
        params.height = getMeasuredHeight() - navView.getMeasuredHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        topViewHeight = topView.getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                velocityTracker.clear();
                velocityTracker.addMovement(event);
                lastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;
                if (!dragging && Math.abs(dy) > touchSlop) {
                    dragging = true;
                }
                if (dragging) {
                    scrollBy(0, (int) -dy);
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                dragging = false;
                if (!overScroller.isFinished()) {
                    overScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                dragging = false;
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                int velocityY = (int) velocityTracker.getYVelocity();
                if (Math.abs(velocityY) > minVelocity) {
                    fling(-velocityY);
                }
                velocityTracker.clear();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void fling(int velocityY) {
        overScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, topViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > topViewHeight) {
            y = topViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }

        topViewHide = getScrollY() == topViewHeight;

        // getScrollY() 是一个绝对值，往上滑，得到的这个值越来越大，往下滑这个值越来越小，且值永远大于 0
        // scrollTo(x,y) y 也是一个绝对值，不是相对值
        // 坐标轴，y 轴，由上往下，从 0 增长
    }

    @Override
    public void computeScroll() {
        if (overScroller.computeScrollOffset()) {
            scrollTo(0, overScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - lastY;
                getCurrentScrollView();

                // innerScrollView.getScrollY() == 0 说明内部的 scrollView 到达顶端
                if (Math.abs(dy) > touchSlop) {
                    dragging = true;
                    if (!topViewHide ||
                            (innerScrollView.getScrollY() == 0 &&
                                    dy > 0)) {
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void getCurrentScrollView() {
        int currentItem = viewPager.getCurrentItem();
        PagerAdapter a = viewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
            Fragment item = (Fragment) fadapter.instantiateItem(viewPager, currentItem);
            innerScrollView = (ScrollView) item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview);
        } else if (a instanceof FragmentStatePagerAdapter) {
            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
            Fragment item = (Fragment) fsAdapter.instantiateItem(viewPager, currentItem);
            innerScrollView = (ScrollView) item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview);
        }

    }
}
