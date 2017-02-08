package com.mybooks.view.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jty.adapterutils.CommonAdapter;
import com.jty.adapterutils.ViewHolder;
import com.jty.utils.T;
import com.jty.utils.TimeUtils;
import com.mybooks.R;
import com.mybooks.models.DataRange;
import com.mybooks.presenter.db.AccountDBDAO;
import com.mybooks.presenter.db.PersonDBDDAO;
import com.mybooks.presenter.logic.MainActivityServer;
import com.mybooks.view.CornerListView;
import com.mybooks.view.DragLayout;
import com.mybooks.view.MyDatePickerDialog;
import com.mybooks.view.PieChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by JTY on 2016/08/11
 */
public class MainActivity extends AppCompatActivity

{

    private Intent intent;
    /**
     * DB and data
     */
    private String name;

    AccountDBDAO accountDBDAO;
    PersonDBDDAO personDBDDAO;

    /**
     * ListView
     */
    @BindView(R.id.lv_main_calculation)
    CornerListView lv_main;
    List<Map<String,String>> maps;


    @BindView(R.id.lv_main_datareport)
    CornerListView lv_data;


    @BindView(R.id.main_user_name)
    TextView user_name;

    @BindView(R.id.monthfind_bt)
    Button monthBtn;

    @BindView(R.id.yearfind_bt)
    Button yearBtn;


    List<DataRange> dataRanges;




    /**
     * Account
     */
    private int  inComeRatio;
    private int  spendRatio;


    @BindView(R.id.ll_piechart)
    LinearLayout ll_piechart;


    /**
     * Sreen and Gestures
     */


    @BindView(R.id.tv_main_time)
    TextView tv_time;
    @BindView(R.id.dl)
    DragLayout dl;

    private boolean isEnter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initViews();
    }

         @Subscribe(threadMode = ThreadMode.MAIN)
         public void onUserEvent(String mainMSG) {

         }

        Handler handler = new Handler();
        Runnable postAtTime = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(postAtTime,1000);
                tv_time.setText(TimeUtils.NowTime());
            }
        };
    private void setData() {
        //设置时间
        handler.post(postAtTime);

        //获取总收入和总支出
        accountDBDAO = new AccountDBDAO(this);
        float inCome = accountDBDAO.findAllInCome(name);
        float spend = accountDBDAO.findAllSpend(name);
        //饼状图
        setPieRatio(inCome,spend);
        PieChart mPieChart = new PieChart();
        mPieChart.PieChart(this,ll_piechart,inComeRatio,spendRatio);

        //获取数据
        maps = MainActivityServer.getData(inCome,spend);

        SimpleAdapter adapter = new SimpleAdapter(this,maps,R.layout.money_item,
                new String[]{"MyMoneyName","MyMoneyNum"},
                new int[]{R.id.item_money_name,R.id.item_money_num});

        lv_main.setAdapter(adapter);

        try {
            dataRanges = MainActivityServer.getAllData(this, name);
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        CommonAdapter commonAdapter = new CommonAdapter<DataRange>(
                this,dataRanges,R.layout.listview_datareport) {
            @Override
            public void convert(ViewHolder helper, DataRange item) {
                helper.setText(R.id.report_datarange,item.getTimeRange());
                helper.setText(R.id.report_income,item.getInCome());
                helper.setText(R.id.report_spend,item.getSpend());
            }
        };

        lv_data.setAdapter(commonAdapter);
    }


    private void initViews() {
        name = getIntent().getStringExtra("name");
        if(name == null){
            intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();

        }else{
            //initImm();
            user_name.setText(name);

            setData();
            setClick();
        }


    }





    private void setClick() {
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, DetailBillActivity.class);
                switch (position) {

                    case 0:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "收入账单");
                        break;
                    case 1:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "支出账单");
                        break;

                    case 2:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "详细账单");
                        break;
                }
                startActivity(intent);
            }
        });

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, DetailBillActivity.class);
                switch (position) {
                    case 0:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "今日账单");
                        break;

                    case 1:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "今月账单");
                        break;

                    case 2:
                        intent.putExtra("name", name);
                        intent.putExtra("title", "今年账单");
                        break;
                }
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setPieRatio(float inCome, float spend) {
        if(inCome-spend<0){
            inComeRatio=0;
            spendRatio=1;
        }else if(inCome==spend){
            inComeRatio=1;
            spendRatio=1;
        }else{
            inComeRatio = (int)inCome;
            spendRatio = (int)spend;
        }
    }


    @OnClick({R.id.bt_main_addnotes,R.id.bt_main_update,R.id.bt_main_ok,R.id.bt_main_cancel,R.id.all_data_bt
                    ,R.id.yearfind_bt,R.id.monthfind_bt})
    void click(View view){
        switch(view.getId()){
            case R.id.yearfind_bt:

                MyDatePickerDialog.showyearPickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if(isEnter) {
                            intent = new Intent(MainActivity.this, DetailBillActivity.class);
                            intent.putExtra("time", year+"%");
                            intent.putExtra("name", name);
                            intent.putExtra("title", year + "年度账单");
                            startActivity(intent);

                            isEnter = false;
                        }
                    }
                });
                isEnter = true;
                break;

            case R.id.monthfind_bt:
                MyDatePickerDialog.showPickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if(isEnter) {

                            intent = new Intent(MainActivity.this, DetailBillActivity.class);
                            intent.putExtra("time", year + "/" + (monthOfYear + 1) + "%");
                            intent.putExtra("name", name);
                            intent.putExtra("title", year + "年" + (monthOfYear + 1) + "月度账单");
                            startActivity(intent);
                            isEnter = false;
                        }
                    }
                });

                isEnter = true;

                break;

            case R.id.bt_main_addnotes:
                intent = new Intent(MainActivity.this, AddBooksActivity.class);
                intent.putExtra("title",getResources().getString(R.string.main_add_notes));
                intent.putExtra("name", name);
                // 传值 帐户名
                startActivity(intent);
                break;

            case R.id.bt_main_update:
                dl.open();
                break;
            case R.id.bt_main_cancel:
                personDBDDAO = new PersonDBDDAO(this);
                personDBDDAO.updateLoginCancel(name);
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.all_data_bt:
                intent = new Intent(MainActivity.this, DetailBillActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("title", "详细账单");
                startActivity(intent);

                break;

            case R.id.bt_main_ok:
                intent = new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("title","修改密码");
                intent.putExtra("name",name);
                startActivity(intent);
                break;

            default:break;
        }

    }



         @Override
         public boolean onKeyDown(int keyCode, KeyEvent event) {
             //应该给个提示吗？
             if(event.getAction()==KeyEvent.ACTION_DOWN){
                 // 创建退出对话框
                 AlertDialog isExit = new AlertDialog.Builder(this).create();
                 // 设置对话框标题
                 isExit.setTitle("温馨提示");
                 // 设置对话框消息
                 isExit.setMessage("确定要退出吗");
                 // 添加选择按钮并注册监听
                 isExit.setButton(DialogInterface.BUTTON_POSITIVE, "确定", listener);
                 isExit.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", listener);
                 // 显示对话框
                 isExit.show();
             }
             return super.onKeyDown(keyCode, event);
         }
         DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                if(which==DialogInterface.BUTTON_POSITIVE){
                    T.showShort(MainActivity.this,"欢迎下次使用！");
                    finish();
//                }else if(which==DialogInterface.BUTTON_NEGATIVE){
//                    return;
                }
             }
         };


         @Override
         protected void onDestroy() {
             super.onDestroy();
             EventBus.getDefault().post("closeLogin");
             EventBus.getDefault().unregister(this);
         }

}
