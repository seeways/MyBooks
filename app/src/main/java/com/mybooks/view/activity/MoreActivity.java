package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.jty.utils.T;
import com.mybooks.R;
import com.mybooks.models.Account;
import com.mybooks.presenter.db.AccountDBDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JTY on 2016/8/15 0015.
 * 更多
 */
public class MoreActivity extends Activity{

    @BindView(R.id.more_et_moneynum)
    EditText et_moneynum;
    @BindView(R.id.more_et_remark)
    EditText et_remark;
    @BindView(R.id.more_et_time)
    EditText et_time;

    @BindView(R.id.more_sp_type)
    Spinner type_spn;
    @BindView(R.id.more_sp_earnings)
    Spinner earnings_spn;


    private AccountDBDAO accountDBDAO;
    private String time;
    private String name;
    private String title;
    private String id;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreactivity_layout);

        ButterKnife.bind(this);

        getData();

        initView();


    }

    private void getData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        time = intent.getStringExtra("time");
    }

    private void initView() {
        /*Init SpinnerView*/
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.type));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spn.setAdapter(typeAdapter);

        ArrayAdapter<String> earningsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.earnings));
        earningsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        earnings_spn.setAdapter(earningsAdapter);

        /*Init DB*/
        accountDBDAO = new AccountDBDAO(this);
        Account account = accountDBDAO.findInfoById(id);
        /*Init Other Data View*/
        et_moneynum.setText(String.valueOf(account.getMoney()));
        et_remark.setText(account.getRemark());
        et_time.setText(account.getTime());


        switch (account.getType()) {
            case "衣服鞋帽":
                type_spn.setSelection(0);
                break;
            case "餐饮零食":
                type_spn.setSelection(1);
                break;
            case "住房用度":
                type_spn.setSelection(2);
                break;
            case "交通出行":
                type_spn.setSelection(3);
                break;
            case "聚会招待":
                type_spn.setSelection(4);
                break;
            case "生活用度":
                type_spn.setSelection(5);
                break;
            case "工资奖金":
                type_spn.setSelection(6);
                break;
            case "投资盈利":
                type_spn.setSelection(7);
                break;
            case "其他":
                type_spn.setSelection(8);
                break;
        }
        /*is earnings*/
        if (account.isEarnings()) {
            earnings_spn.setSelection(1);
        }else{
            earnings_spn.setSelection(0);
        }
    }

    @OnClick({R.id.bt_more_update,R.id.bt_more_cancel})
    void more_inclick(View view){
        switch(view.getId()){
            case R.id.bt_more_update:
                float money = 0;
                String type;
                boolean earning;
                String remark;
                time = et_time.getText().toString();
                if (et_moneynum.getText().toString().trim().equals("")) {
                    T.showShort(this,R.string.money_not_null);
                }else{
                    money = Float.parseFloat(et_moneynum.getText().toString().trim());
                }

                remark = et_remark.getText().toString().trim();
                type = type_spn.getSelectedItem().toString();
                earning = earnings_spn.getSelectedItem().toString().equals("收入");
                accountDBDAO.update(id,time,money,type,earning,remark);
                T.showShort(this, "update success!");

                finish();
                break;
            case R.id.bt_more_cancel:
                Intent intent = new Intent(this,DetailBillActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("title",title);
                intent.putExtra("time",time);
                startActivity(intent);
                finish();
                break;
        }
    }

}
