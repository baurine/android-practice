package com.baurine.customeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CustomImageView extends View {

    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_CENTER = 1;

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Bitmap mImage;
    private int mImageScaleType;

    private Rect mTextBound;
    private Paint mPaint;
    private Rect mRect;

    private int mWidth;
    private int mHeight;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(context, attrs, defStyleAttr);
        init();
    }


    private void parseAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);

            switch (attr) {
                case R.styleable.CustomImageView_titleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int)
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
                                    getResources().getDisplayMetrics()));
                    break;
                case R.styleable.CustomImageView_titleTextColour:
                    mTitleTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScaleType = a.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
                    break;
            }
        }

        a.recycle();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);

        mRect = new Rect();
        mTextBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
            int desiredByTitle = mTextBound.width() + getPaddingLeft() + getPaddingRight();
            int desiredByImage = mImage.getWidth() + getPaddingLeft() + getPaddingRight();
            int desired = Math.max(desiredByImage, desiredByTitle);

            if (widthMode == MeasureSpec.AT_MOST) {
                mWidth = Math.min(widthSize, desired);
            } else {
                mWidth = desired;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mTextBound);
            int desiredHeight = mImage.getHeight() + mTextBound.height() + getPaddingTop() +
                    getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desiredHeight, heightSize);
            } else {
                mHeight = desiredHeight;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw border
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, mWidth - getPaddingLeft()
                    - getPaddingRight(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTitleText, mWidth / 2 - mTextBound.width() / 2, mHeight - getPaddingBottom(),
                    mPaint);
        }

        mRect.bottom -= mTextBound.height();

        if (mImageScaleType == IMAGE_CENTER) {
            mRect.left = mWidth / 2 - mImage.getWidth() / 2;
            if (mRect.left < getPaddingLeft()) {
                mRect.left = getPaddingLeft();
            }
            mRect.right = mWidth / 2 + mImage.getWidth() / 2;
            if (mRect.right > mWidth - getPaddingRight()) {
                mRect.right = mWidth - getPaddingRight();
            }
            mRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            if (mRect.top < getPaddingTop()) {
                mRect.top = getPaddingTop();
            }
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            if (mRect.bottom > mHeight - getPaddingBottom() - mTextBound.height()) {
                mRect.bottom = mHeight - getPaddingBottom() - mTextBound.height();
            }
        }
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }
}
