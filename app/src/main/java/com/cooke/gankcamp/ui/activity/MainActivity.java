package com.cooke.gankcamp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.presenter.MainPresenter;
import com.cooke.gankcamp.ui.adapter.GankFragmentAdapter;
import com.cooke.gankcamp.ui.view.IMainView;
import com.cooke.gankcamp.util.GoogleLogin;
import com.cooke.gankcamp.util.ToastUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView,View.OnClickListener,GoogleLogin.GoogleSignListener {

    private MainPresenter mPresenter;

    private GankFragmentAdapter mFragmentAdapter;

    private GoogleLogin mGoogleSign;

    @BindView(R.id.toolbar)
    Toolbar tool_bar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawer_layout;
    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.ll_about)
    LinearLayout ll_about;
    @BindView(R.id.ll_sort)
    LinearLayout ll_sort;
    @BindView(R.id.btn_sign_google)
    SignInButton btn_sign_google;
    @BindView(R.id.btn_signout_google)
    Button btn_signout_google;
    @BindView(R.id.tv_google_email)
    TextView tv_google_email;
    @BindView(R.id.tv_google_name)
    TextView tv_google_name;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);

        setSupportActionBar(tool_bar);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showDrawer();
            }
        });

        tab_layout.addTab(tab_layout.newTab().setText("热门"));
        tab_layout.addTab(tab_layout.newTab().setText("妹子"));
        tab_layout.setupWithViewPager(view_pager);

        mFragmentAdapter = new GankFragmentAdapter(getSupportFragmentManager());
        view_pager.setAdapter(mFragmentAdapter);

        ll_about.setOnClickListener(this);
        ll_sort.setOnClickListener(this);
        btn_sign_google.setOnClickListener(this);
        btn_signout_google.setOnClickListener(this);

        mGoogleSign = new GoogleLogin(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                ToastUtil.showToast(MainActivity.this,"连接谷歌服务失败");
            }
        });
        mGoogleSign.setGoogleSignListener(this);



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
    public void showDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void signIn() {
        mGoogleSign.signIn();
    }

    @Override
    public void signOut() {
        mGoogleSign.signOut();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_about:
                Intent toAboutPhone = new Intent(MainActivity.this,AboutPhoneActivity.class);
                startActivity(toAboutPhone);
                break;
            case R.id.ll_sort:
                Intent toSortGank = new Intent(MainActivity.this,SortGankActvity.class);
                startActivity(toSortGank);
                break;
            case R.id.btn_sign_google:
                mPresenter.signIn();
                break;
            case R.id.btn_signout_google:
                mPresenter.signOut();
                break;
        }
    }

    @Override
    public void onGoogleLoginSuccess(GoogleSignInAccount account) {
        btn_signout_google.setVisibility(View.GONE);
        btn_signout_google.setVisibility(View.VISIBLE);
        ToastUtil.showToast(MainActivity.this,"登录成功");
        String name = account.getDisplayName();
        String email = account.getEmail();
        tv_google_email.setText(email);
        tv_google_name.setText(name);

    }

    @Override
    public void onGoogleLoginFail() {
        ToastUtil.showToast(MainActivity.this,"登录失败，请稍后重试");
    }

    @Override
    public void onGoogleLogoutSuccess() {
        btn_signout_google.setVisibility(View.VISIBLE);
        btn_signout_google.setVisibility(View.GONE);
        ToastUtil.showToast(MainActivity.this,"已退出登录");
        tv_google_name.setText("");
        tv_google_email.setText("未登录");
    }

    @Override
    public void onGoogleLogoutFail() {
        ToastUtil.showToast(MainActivity.this,"退出登录失败，请稍后重试");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==GoogleLogin.RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mGoogleSign.handleSignInResult( result ) ;

        }
    }
}
