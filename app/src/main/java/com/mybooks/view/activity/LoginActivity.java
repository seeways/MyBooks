package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jty.utils.T;
import com.mybooks.R;
import com.mybooks.models.Person;
import com.mybooks.presenter.db.PersonDBDDAO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JTY on 2016/8/11 .
 * login
 */
public class LoginActivity extends Activity{
    PersonDBDDAO personDB;
    Person person;

    @BindView(R.id.login_username_et)
    EditText username_et;
    @BindView(R.id.login_password_et)
    EditText password_et;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);

        isLogin();

    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onUserEvent(String mainMSG) {
//        //如果多个消息，可在实体类中添加type区分消息
//        if(mainMSG.equals("closeLogin")){
//            isLogin();
//        }
//    }

    private void isLogin() {
        personDB = new PersonDBDDAO(LoginActivity.this);
        person = personDB.findLoginOK();

        if(person!=null){
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("name",person.getName());
            startActivity(intent);
            this.finish();
        }

    }

    @OnClick({R.id.login_btn,R.id.login_register,R.id.login_forgot_password})
    void loginclick(View view){
        String username = username_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        switch(view.getId()){
            case R.id.login_btn:
                if(username.equals("")){
                    T.showShort(this,R.string.username_not_null);
                    break;
                }
                if(password.equals("")){
                    T.showShort(this,R.string.password_not_null);
                    break;
                }
                personDB = new PersonDBDDAO(this);
                boolean result = personDB.findPerson(username);
                if(result){
                    result = personDB.findLogin(username,password);
                    if(result){
                        personDB.updataLoginOK(username);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("name",username);
                        startActivity(intent);
                        finish();
                    }else{
                        T.showShort(this,"密码错误，请重试！");
                    }
                }else{
                    T.showShort(this,R.string.username_not_exist);
                }
                break;

            case R.id.login_register:
               Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.login_forgot_password:
                Toast.makeText(this,R.string.waiting,Toast.LENGTH_SHORT).show();
                break;

                default:break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);

    }


}
