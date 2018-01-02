package com.cooke.gankcamp.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.cooke.gankcamp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

/**
 * Created by kch on 2017/12/20.
 */

public class GoogleLogin {

    private GoogleSignInOptions mGso;
    private GoogleApiClient mGoogleApiClient;
    private FragmentActivity context;
    private GoogleApiClient.OnConnectionFailedListener mListener;

    public static final int RC_SIGN_IN = 100;//request_code

    public GoogleLogin(FragmentActivity context,GoogleApiClient.OnConnectionFailedListener listener){
        this.context = context;
        this.mListener = listener;
        //初始化谷歌登录服务
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestIdToken( context.getString(R.string.google_server_client_id))
                .requestProfile()
                .build();

        mGoogleApiClient =  new GoogleApiClient.Builder(context)
                .enableAutoManage(context,mListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGso)
                .build();
    }

    /**
     * 登录
     */
    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * 退出登录
     */
    public void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    if (mGoogleSignListener!=null){
                        mGoogleSignListener.onGoogleLogoutSuccess();;
                    }
                }else {
                    if (mGoogleSignListener!=null){
                        mGoogleSignListener.onGoogleLogoutFail();
                    }
                }
            }
        });
    }

    /**
     * 处理登录逻辑，该方法在onActivityResult中调用
     * @param result
     */
    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            if (mGoogleSignListener!=null){
                mGoogleSignListener.onGoogleLoginSuccess(acct);
            }
        }else {
            if (mGoogleSignListener!=null){
                mGoogleSignListener.onGoogleLoginFail();
            }
        }
    }

    /**
     * 接口回调，处理登录和退出登录
     */
    public interface GoogleSignListener{
        public void onGoogleLoginSuccess(GoogleSignInAccount account);
        public void onGoogleLoginFail();
        public void onGoogleLogoutSuccess();
        public void onGoogleLogoutFail();
    }

    private GoogleSignListener mGoogleSignListener;

    public void setGoogleSignListener(GoogleSignListener listener){
        this.mGoogleSignListener = listener;
    }

}
