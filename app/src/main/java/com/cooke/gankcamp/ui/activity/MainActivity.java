package com.cooke.gankcamp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.presenter.MainPresenter;
import com.cooke.gankcamp.ui.adapter.GankFragmentAdapter;
import com.cooke.gankcamp.ui.view.IMainView;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView,View.OnClickListener {

    private MainPresenter mPresenter;

    private GankFragmentAdapter mFragmentAdapter;

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_about:
                Intent toAboutPhone = new Intent(MainActivity.this,AboutPhoneActivity.class);
                startActivity(toAboutPhone);
                break;
        }
    }
}
