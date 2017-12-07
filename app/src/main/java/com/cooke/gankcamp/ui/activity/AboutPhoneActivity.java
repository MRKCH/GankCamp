package com.cooke.gankcamp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.receiver.BatteryReceiver;
import com.cooke.gankcamp.ui.view.IAboutPhoneView;
import com.cooke.gankcamp.util.SystemUtil;
import com.cooke.gankcamp.util.ToastUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class AboutPhoneActivity extends BaseActivity implements IAboutPhoneView, View.OnClickListener {



    @BindView(R.id.tv_device_model)
    TextView tv_device_model;
    @BindView(R.id.tv_device_electric1)
    TextView tv_device_electric1;
    @BindView(R.id.tv_device_imei)
    TextView tv_device_imei;
    @BindView(R.id.tv_device_meid)
    TextView tv_device_meid;
    @BindView(R.id.tv_device_language)
    TextView tv_device_language;
    @BindView(R.id.tool_bar)
    Toolbar tool_bar;

    private BatteryReceiver mBatterReceiver;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_about_phone;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tool_bar.setTitle("关于本机");
        setSupportActionBar(tool_bar);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_device_model.setText(SystemUtil.getSystemModel());
        tv_device_language.setText(SystemUtil.getSystemLanguage());
        tv_device_meid.setText(SystemUtil.getAndroidId(this));

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        mBatterReceiver = new BatteryReceiver(tv_device_electric1);
        registerReceiver(mBatterReceiver,intentFilter);

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            tv_device_imei.setText(SystemUtil.getIMEI(AboutPhoneActivity.this));
                        }else {
                            ToastUtil.showToast(AboutPhoneActivity.this,"没有手机设备信息权限，获取imei失败");
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showLoading(String msg, int progress) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideLoading(String msg) {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showErrorMsg(String msg, String content) {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatterReceiver);
    }
}
