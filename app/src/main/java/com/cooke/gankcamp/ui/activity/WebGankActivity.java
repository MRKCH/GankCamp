package com.cooke.gankcamp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.presenter.WebPresenter;
import com.cooke.gankcamp.ui.view.IWebView;
import com.cooke.gankcamp.util.ToastUtil;

import butterknife.BindView;

/**
 * Created by kch on 2017/12/1.
 */

public class WebGankActivity extends BaseActivity implements IWebView {
    public static final String WEB_TITLE  = "web_title";
    public static final String WEB_URL = "web_url";

    private WebPresenter mPresenter;

    private String web_url;
    private String web_title;
    @BindView(R.id.web_view)
    WebView web_view;

    @BindView(R.id.tool_bar)
    Toolbar tool_bar;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;
    @Override
    protected int initLayoutId() {
        return R.layout.activity_webgank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        web_url = intent.getStringExtra(WEB_URL);
        web_title = intent.getStringExtra(WEB_TITLE);


        //设置title要在setSupportActionBar之前
        tool_bar.setTitle(web_title);

        setSupportActionBar(tool_bar);

        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initWebView();

        mPresenter = new WebPresenter();
        mPresenter.attachView(this);



        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                web_view.reload();
            }
        });

        refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                refresh_layout.setRefreshing(true);
                web_view.loadUrl(web_url);

            }
        });
    }

    private void initWebView() {

        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                refresh_layout.setRefreshing(true);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refresh_layout.setRefreshing(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ToastUtil.showToast(WebGankActivity.this,error.toString());
            }
        });

        WebSettings settings=web_view.getSettings();

        //设置WebView支持广泛的视窗
        settings.setUseWideViewPort(true);

        //支持手势缩放
        settings.setBuiltInZoomControls(true);

        //设置WebView 支持加载更多格式页面
        settings.setLoadWithOverviewMode(true);

        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        settings.setAppCacheEnabled(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        settings.setJavaScriptEnabled(true);

        settings.setDomStorageEnabled(true);


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
}
