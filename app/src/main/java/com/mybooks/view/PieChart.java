package com.mybooks.view;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by JTY on 2016/8/11 0011.
 */
public class PieChart {

    Context context;
    LinearLayout pieChart;
    GraphicalView pieView;
    int colors[] = {Color.GREEN,Color.RED};

    public void PieChart(Context context, LinearLayout pieChart,int inCome ,int spend) {
        this.context = context;
        this.pieChart = pieChart;
        double[] values = {inCome,spend};


        CategorySeries series = new CategorySeries("title");
        series.add("收入",values[0]);
        series.add("支出", values[1]);

        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setBackgroundColor(0);
        renderer.setApplyBackgroundColor(true);

        renderer.setLabelsColor(Color.WHITE);
        renderer.setLabelsTextSize(10);

        renderer.setLegendTextSize(10);
        renderer.setMargins(new int[]{0,20,0,0});

        renderer.setChartTitle("收支比例");
        renderer.setChartTitleTextSize(10);

        for(int color: colors){
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        pieView = ChartFactory.getPieChartView(context,series,renderer);
        pieChart.removeAllViews();
        pieChart.addView(pieView,new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }


}
