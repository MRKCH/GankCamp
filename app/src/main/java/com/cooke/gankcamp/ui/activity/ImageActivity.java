package com.cooke.gankcamp.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cooke.gankcamp.R;
import com.cooke.gankcamp.presenter.ImageGirlPresenter;
import com.cooke.gankcamp.ui.view.IImageGirlView;
import com.cooke.gankcamp.util.ToastUtil;

import butterknife.BindView;

public class ImageActivity extends BaseActivity implements IImageGirlView, View.OnClickListener {

    public static final String IMG_URL = "img_url";

    private String img_url;

    private ImageGirlPresenter mPresenter;
    @BindView(R.id.img_girl)
    ImageView img_girl;
    @BindView(R.id.btn_download)
    Button btn_download;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ImageGirlPresenter(this);
        mPresenter.attachView(this);

        img_url = getIntent().getStringExtra(IMG_URL);

        mPresenter.showImage(img_url);
        mPresenter.requestPermission();
        btn_download.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    ProgressDialog dialog;
    @Override
    public void showLoading(String msg) {
        if (dialog==null)
            dialog = new ProgressDialog(this);
        dialog.setMessage(msg);
        dialog.show();
    }

    @Override
    public void showLoading(String msg, int progress) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideLoading(String msg) {
        if (dialog!=null&&dialog.isShowing())
            dialog.dismiss();
        ToastUtil.showToast(this,msg);
    }


    @Override
    public void showMsg(String msg) {
        ToastUtil.showToast(this,msg);
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
    public void showImage(String url) {
        Glide.with(this).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().into(img_girl);
    }

    @Override
    public void downloadImage(String url) {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                mPresenter.downloadImage(img_url);
                break;
        }
    }
}
