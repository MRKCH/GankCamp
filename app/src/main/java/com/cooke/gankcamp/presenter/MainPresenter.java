package com.cooke.gankcamp.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.cooke.gankcamp.ui.view.IMainView;



/**
 * Created by kch on 2017/11/27.
 */

public class MainPresenter extends BasePresenter<IMainView> {


    public void showDrawer(){
        mView.showDrawer();
    }

}
