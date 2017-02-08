package com.mybooks.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JTY on 2016/8/16 0016.
 */
public class MySideSliding extends ViewGroup{
    ViewDragHelper mDragHelper;
    ViewGroup mLeftContent;
    ViewGroup mRightContent;

    private int mRange;

    public MySideSliding(Context context) {
        super(context);
    }

    public MySideSliding(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySideSliding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }

    /*this my callback*/
    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        /**
         * 根据建议值修正将要移动的位置   此时并没有发生真正的移动(左右)
         *
         * @param child    当前拖拽的view
         * @param left     新的位置的建议值  oldLeft + dx
         * @param dx       变化量   和变化之前位置的差值
         * @return left
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mRightContent) {
                left = fixLeft(left);
            }
            return left;
        }

        private int fixLeft(int left) {
            if (left < 0) {
                return 0;
            } else if (left > mRange) {
                return mRange;
            }
            return left;
        }

        /**
         * 根据返回结果决定当前child是否可以拖拽
         * 尝试捕获的时候调用，如果返回的是主面板，那么负面版是不能被调用的
         * @param child    当前被拖拽的view
         * @param pointerId    区分多点触摸的id
         * @return  返回true 是都可以拖拽
         *          返回child == mLeftContent   左侧可以移动
         *          返回child == mMainContent   右侧可以移动
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
    };
    /**
     * 2、传递触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     *   当xml填充完的时候去掉用，在这里我们可以找到我们要去操控的那两个布局
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLeftContent = (ViewGroup)getChildAt(0);

        mRightContent = (ViewGroup) getChildAt(1);

    }

    /**
     * 当尺寸变化的时候去调用
     * This is called during layout when the size of this view has changed
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int mWidth = getMeasuredWidth();
        /**
         * 自定义左侧能拖出来的宽度
         */
        mRange = (int) (mWidth * 0.7);
    }

    /**
     * 当view位置改变的时候，处理要做的事情，更新状态，伴随动画，重绘界面
     *
     * 此时view已经发生了位置的改变
     *
     * @param changedView   改变的位置view
     * @param left   新的左边值
     * @param top
     * @param dx   水平变化量
     * @param dy
     */


}
