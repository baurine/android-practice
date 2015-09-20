package com.baurine.customeview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class CustomProgressBar extends View {

    private int firstColor;
    private int secondColor;
    private int circleWidth;
    private int speed;

    private Paint paint;

    private int progress = 0;
    private boolean next = false;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(context, attrs, defStyleAttr);
        init();
    }


    private void parseAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomProgressBar, defStyleAttr, 0);

        firstColor = a.getColor(R.styleable.CustomProgressBar_firstColor, Color.RED);
        secondColor = a.getColor(R.styleable.CustomProgressBar_secondColor, Color.BLUE);
        circleWidth = a.getDimensionPixelSize(R.styleable.CustomProgressBar_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                        getResources().getDisplayMetrics()));
        speed = a.getInt(R.styleable.CustomProgressBar_speed, 20);

        a.recycle();
    }


    private void init() {
        paint = new Paint();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    progress += speed;
//                    if (progress >= 360) {
//                        progress = 0;
//                        next = !next;
//                    }
//                    postInvalidate();
//                    try {
//                        Thread.sleep(100);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        ObjectAnimator progressAnimator =
                ObjectAnimator.ofInt(this, "progress", 0, 360)
                        .setDuration(360 / speed * 1000);
        progressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                next = !next;
            }
        });

        progressAnimator.start();
    }

    public void setProgress(int value) {
        progress = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getMeasuredWidth() / 2;
        int radius = center - circleWidth / 2;
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);

        paint.setStrokeWidth(circleWidth);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        if (!next) {
            paint.setColor(firstColor);
            canvas.drawCircle(center, center, radius, paint);
            paint.setColor(secondColor);
            canvas.drawArc(oval, -90, progress, false, paint);
        } else {
            paint.setColor(secondColor);
            canvas.drawCircle(center, center, radius, paint);
            paint.setColor(firstColor);
            canvas.drawArc(oval, -90, progress, false, paint);
        }
    }
}
