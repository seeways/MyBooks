package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jty.utils.T;
import com.mybooks.R;
import com.mybooks.presenter.db.PersonDBDDAO;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JTY on 2016/8/17 0017.
 */
public class UpdateActivity extends Activity{

    @BindViews({R.id.registe_username_et,R.id.registe_password_et,R.id.registe_confirm_password_et})
    List<EditText> updata_ets;

    @BindView(R.id.regist_title_tv)
    TextView title_tv;

    @BindView(R.id.registe_btn)
    Button update_btn;

    String name;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        ButterKnife.bind(this);

        initWidGets();


    }

    private void initWidGets() {

        Intent intent = getIntent();
        title =  intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        title_tv.setText(title);
        updata_ets.get(0).setText(name);
        update_btn.setText(R.string.confirm);
    }

    private void update_user() {
        String newName=null;
        String pass1 = null;
        String pass2 = null;
        if(updata_ets.get(0).getText().toString().trim().equals("")) {
            T.showShort(getApplicationContext(), R.string.username_not_null);
        }else{
            newName = updata_ets.get(0).getText().toString().trim();
        }
        if(updata_ets.get(1).getText().toString().trim().equals("")){
            T.showShort(getApplicationContext(),R.string.password_not_null);

        }else{
            pass1 = updata_ets.get(1).getText().toString().trim();
        }

        if(updata_ets.get(2).getText().toString().trim().equals(pass1)){
            pass2 = updata_ets.get(2).getText().toString().trim();
        }else{
            T.showShort(getApplicationContext(),R.string.username_not_same);

        }

        PersonDBDDAO personDBDDAO = new PersonDBDDAO(getApplicationContext());
        personDBDDAO.updateUser(name, newName, pass2);
        Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT)
                .show();
        finish();

    }


    @OnClick({R.id.registe_btn,R.id.registe_cancel})
    void update(View view){
       switch (view.getId()){
           case R.id.registe_btn:
               update_user();
               break;
           case R.id.registe_cancel:
                finish();
               break;
       }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
