package com.cooke.gankcamp.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.cooke.gankcamp.Interface.GankService;
import com.cooke.gankcamp.R;
import com.cooke.gankcamp.contsants.GankUrl;
import com.cooke.gankcamp.model.GirlImageModel;
import com.cooke.gankcamp.ui.view.IImageGirlView;
import com.cooke.gankcamp.util.FileUtil;
import com.cooke.gankcamp.util.ToastUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by kch on 2017/12/5.
 */

public class ImageGirlPresenter extends BasePresenter<IImageGirlView> {

    private static final String TAG = "ImageGirlPresenter";
    private GirlImageModel mModel;
    private Activity mContext;

    public ImageGirlPresenter(Activity context){
        mModel = new GirlImageModel();
        this.mContext = context;
    }

    public void showImage(String url){
        mView.showImage(url);
    }

    public void downloadImage(final String url){

        String rootPath = FileUtil.getExternalSDCardPath()!=null?FileUtil.getExternalSDCardPath():FileUtil.getInnerSDCardPath();
        final String appDir = rootPath+File.separator+ mContext.getPackageName()+File.separator+"downloadImage";
        File file = new File(appDir);
        if (!file.exists()){
            file.mkdirs();
        }
        final String imgName = url.substring(url.lastIndexOf("/"));
        final String imagePath =appDir+imgName;
        final File imgFile = new File(imagePath);
        if (imgFile.exists()){
            mView.showMsg("该图片已下载");
            return;
        }
        mModel.downloadGirlImage(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<okhttp3.ResponseBody>() {



                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading("正在下载...");

                    }

                    @Override
                    public void onNext(ResponseBody value) {

                        if (!imgFile.exists()) {
                            if (FileUtil.saveImage(imagePath, value.byteStream())) {
                                mView.hideLoading("下载成功");


                                // 通知图库更新
                                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,  Uri.parse("file://" + imgFile.getAbsolutePath())));
                            } else {
                                mView.hideLoading("下载失败");
                            }
                        }else {
                            mView.showMsg("该图片已下载");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading("下载失败");
                        Logger.i(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public  void requestPermission(){
        RxPermissions rxPermission = new RxPermissions(mContext);
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted){

                        }else if (permission.shouldShowRequestPermissionRationale){
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            ToastUtil.showToast(mContext,"没有权限");
                        }else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            ToastUtil.showToast(mContext,"你需要前往设置打开权限");
                        }
                    }
                });
    }
}
