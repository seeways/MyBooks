package com.mybooks.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jty.adapterutils.CommonAdapter;
import com.jty.adapterutils.ViewHolder;
import com.jty.utils.L;
import com.jty.utils.T;
import com.jty.utils.TimeUtils;
import com.mybooks.R;
import com.mybooks.models.Account;
import com.mybooks.presenter.db.AccountDBDAO;
import com.mybooks.view.CornerListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JTY on 2016/8/15 0015.
 * 详细
 */


public class DetailBillActivity extends Activity{

    private String title;
    private String name;
    private String time;
    private Intent intent;
    private AccountDBDAO accountDBDAO;

    @BindView(R.id.detailbill_tv)
    TextView tv_title;


    @BindView(R.id.detailbill_ll)
    CornerListView ll_detaildata;

    List<Account> accounts;
    String year,month,days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databill_layout);

        ButterKnife.bind(this);
        L.e("onCreate");
        initViews();

        getData();
    }


    private void getData() {

        accountDBDAO = new AccountDBDAO(this);
        year = TimeUtils.getLikeYear();
        month = TimeUtils.getLikeMonth();
        days = TimeUtils.getDayTime();

        try {
            if (title.equals("收入账单")) {
                accounts = accountDBDAO.findInComeByName(name);
            } else if (title.equals("支出账单")) {
                accounts = accountDBDAO.findSpendByName(name);
            } else if (title.equals("详细账单")) {
//                accounts = accountDBDAO.findAllByName(name);
                accounts = accountDBDAO.findAll();
            } else if (title.equals("今日账单")) {
                accounts = accountDBDAO.findAllByTime(name,days);
            } else if (title.equals("今月账单")) {
                accounts = accountDBDAO.findAllByTime(name, month);
            } else if (title.equals("今年账单")) {
                accounts = accountDBDAO.findAllByTime(name, year);
            }else if(title.substring(title.length()-4,title.length()).equals("年度账单")){
                accounts = accountDBDAO.findAllByTime(name, time);
            }else if(title.substring(title.length()-4,title.length()).equals("月度账单")){
                accounts = accountDBDAO.findAllByTime(name, time);
            }
        } catch (Exception e) {
            T.showShort(this,"get Data failed!");
            e.printStackTrace();
        }

        ll_detaildata.setAdapter(new CommonAdapter<Account>(
                this,accounts,R.layout.detailbill_data_title){
            @Override
            public void convert(ViewHolder helper, Account account) {
                helper.setText(R.id.detailbill_time,account.getTime());
                helper.setText(R.id.detailbill_type,account.getType());
                helper.setText(R.id.detailbill_moneynum,account.getMoney()+"");
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("你想干啥？");
        menu.add(0, 0, Menu.NONE, "修改");
        menu.add(0, 1, Menu.NONE, "删除");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case 0:
                intent = new Intent(DetailBillActivity.this, MoreActivity.class);
                intent.putExtra("id", accounts.get(menuInfo.position).getId() + "");
                intent.putExtra("name", name);
                intent.putExtra("time",time);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
                break;
            case 1:
                String accountId = accounts.get(menuInfo.position).getId() + "";
                accountDBDAO.delete(accountId);
                T.showShort(this, "delete success!");
                finish();

                break;
            default:
                return super.onContextItemSelected(item);
        }

        return true;
    }

    private void initViews() {

        intent = this.getIntent();
        if(intent!=null){
            title = intent.getStringExtra("title");
            name = intent.getStringExtra("name");
            time = intent.getStringExtra("time");
            tv_title.setText(title);
        }

        registerForContextMenu(ll_detaildata);
    }


    @Override
    protected void onDestroy() {
        unregisterForContextMenu(ll_detaildata);
        L.e("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
