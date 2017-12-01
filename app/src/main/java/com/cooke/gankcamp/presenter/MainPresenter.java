package com.cooke.gankcamp.presenter;

import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.GankData;
import com.cooke.gankcamp.model.NewsModel;
import com.cooke.gankcamp.ui.view.IMainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by kch on 2017/11/27.
 */

public class MainPresenter extends BasePresenter<IMainView> {

    public void showDrawer(){
        mView.showDrawer();
    }


}
