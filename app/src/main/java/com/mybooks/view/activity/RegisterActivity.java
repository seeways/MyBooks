package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mybooks.R;
import com.mybooks.presenter.db.PersonDBDDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JTY on 2016/8/10 0014.
 * Regist
 */
public class RegisterActivity extends Activity {

    @BindView(R.id.registe_username_et)
    EditText name;
    @BindView(R.id.registe_password_et)
    EditText pass;
    @BindView(R.id.registe_confirm_password_et)
    EditText pass2;

    String username;
    String password;
    String confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registe);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.registe_btn, R.id.registe_cancel})
    void register(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.registe_btn:
                username = name.getText().toString().trim();
                password = pass.getText().toString().trim();
                confirmpass = pass2.getText().toString().trim();

                if(username.equals("")){
                    Toast.makeText(this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                }else
                if(password.equals("")){
                    Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                }else
                if(!confirmpass.equals(password)){
                    Toast.makeText(this,"密码不一致！",Toast.LENGTH_SHORT).show();
                }else {

                    PersonDBDDAO persondao = new PersonDBDDAO(this);
                    persondao.addPerson(username, password);

                    persondao.updataLoginOK(username);

                    intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("name", username);
                    startActivity(intent);
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.registe_cancel:
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


}
