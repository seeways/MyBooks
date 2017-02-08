package com.mybooks.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by JTY on 2016/8/27 0027.
 * datepickerDialog
 */
public class MyDatePickerDialog extends DatePickerDialog{

    public MyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
        this.setTitle("请输入要查询的日期");

    }



    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        super.onDateChanged(view, year, month, day);
        //if you need update title
          this.setTitle("请输入要查询的日期");
    }



    public static void showPickerDialog(final Context context,OnDateSetListener callback) {
        final Calendar cal = Calendar.getInstance();
        MyDatePickerDialog mDialog = new MyDatePickerDialog(context,callback,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        mDialog.show();

        DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow().getDecorView());
        if (dp != null) {
            //0 1 2位置为年月日
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);

        }
    }

    public static void showyearPickerDialog(final Context context,OnDateSetListener callback) {
        final Calendar cal = Calendar.getInstance();
        MyDatePickerDialog mDialog = new MyDatePickerDialog(context,callback,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        mDialog.show();

        DatePicker dp = findDatePicker((ViewGroup) mDialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
        }
    }

    private static DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

}
