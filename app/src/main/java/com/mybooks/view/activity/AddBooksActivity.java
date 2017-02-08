package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jty.utils.T;
import com.jty.utils.TimeUtils;
import com.mybooks.R;
import com.mybooks.presenter.db.AccountDBDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JTY on 2016/8/16 0016.
 */
public class AddBooksActivity extends Activity{

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

    @BindView(R.id.more_title)
    TextView tv;


    private AccountDBDAO accountDBDAO;
    private String name;
    private String title;

    private String time;
    private float money;
    private String type;
    private boolean earning;
    private String remark;

    //String[] types = getResources().getStringArray(R.array.type);


    //String[] earnings = getResources().getStringArray(R.array.earnings);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreactivity_layout);
        ButterKnife.bind(this);

        getIntentData();

        initSpn();

    }

    private void initSpn() {
                /*Init SpinnerView*/
//        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(
//                this,android.R.layout.simple_spinner_item,types);
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        type_spn.setAdapter(typeAdapter);
//
//        ArrayAdapter<String> earningsAdapter = new ArrayAdapter<String>(
//                this,android.R.layout.simple_spinner_item,earnings);
//        earningsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        earnings_spn.setAdapter(earningsAdapter);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        tv.setText(title);
        time = TimeUtils.getDayTime();
        et_time.setText(time);
    }


    @OnClick({R.id.bt_more_cancel,R.id.bt_more_update})
    void addClick(View view){
        switch(view.getId()){
            case R.id.bt_more_update:
                accountDBDAO = new AccountDBDAO(this);


                if(et_moneynum.getText().toString().trim().equals("")){
                    T.showShort(this,R.string.money_not_null);
                    break;
                }else{
                    money = Float.parseFloat(et_moneynum.getText().toString().trim());
                }
                type = type_spn.getSelectedItem().toString();
                remark = et_remark.getText().toString().trim();
                if(earnings_spn.getSelectedItem().toString().equals("收入")){
                    earning = true;
                }else{
                    earning = false;
                }

                accountDBDAO.add(et_time.getText().toString(),money,type,earning,remark,name);
                T.showShort(this, "add sucsess!");
                finish();
                break;

            case R.id.bt_more_cancel:
                finish();
                break;
        }

    }
}
