package com.mybooks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by JTY on 2016/8/24 0024.
 * 这个用于item的侧滑修改和删除
 */
public class SliderListView extends ListView{
    private static final String TAG = "SliderListView";
    private float mX = 0;
    private float mY = 0;
    private int mPosition = -1;
    private boolean isSlide = false;
    private SliderLayout mFocusItem;


    public SliderListView(Context context) {
        super(context);
    }

    public SliderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SliderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 只需要重写onTouchEvent就行了
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                isSlide = false;
                mX = x;
                mY = y;
                int position = pointToPosition((int)x,(int)y);
                if(mPosition != position){
                    mPosition = position;
                    if(mFocusItem != null){
                        mFocusItem.reset();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:

                if(mPosition != -1){
                    if(Math.abs(mY-y)<30 && Math.abs(mX-x)>20){
                        int first = getFirstVisiblePosition();
                        int index = mPosition - first;
                        mFocusItem = (SliderLayout) getChildAt(index);
                        mFocusItem.onTouchEvent(ev);
                        isSlide = true;
                        return true;
                    }

                }
                break;

            case MotionEvent.ACTION_UP:
                if(isSlide){
                    isSlide = false;
                    if(mFocusItem != null){
                        mFocusItem.adjust(mX - x>0);
                        return true;
                    }
                }
                break;
            default:break;

        }

        return super.onTouchEvent(ev);
    }
}
