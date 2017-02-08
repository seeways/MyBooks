package com.mybooks.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mybooks.R;

/**
 * Created by JTY on 2016/8/10 0010.
 */
public class CornerListView extends ListView{

    public CornerListView(Context context) {
        super(context);
    }

    public CornerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CornerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //重写拦截方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int itemNum = pointToPosition(x,y);
                if(itemNum == AdapterView.INVALID_POSITION){
                    break;
                }else
                if (itemNum == 0) {
                    if (itemNum == (getAdapter().getCount() - 1)) {
                        //只有一项
                        setSelector(R.drawable.app_list_corner_round);
                    } else {
                        //第一项
                        setSelector(R.drawable.app_list_corner_round_top);
                    }
                }else if(itemNum == (getAdapter().getCount() - 1)){
                    //最后一项
                    setSelector(R.drawable.app_list_corner_round_bottom);
                }else{
                    //中间一项
                    setSelector(R.drawable.app_list_corner_shape);
                }

                break;

            case MotionEvent.ACTION_UP:
                break;
            default:break;

        }



        return super.onInterceptTouchEvent(ev);
    }
}
