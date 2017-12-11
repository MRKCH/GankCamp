package com.cooke.gankcamp.presenter;

import android.content.Context;

import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.SortData;
import com.cooke.gankcamp.model.SortGankModel;
import com.cooke.gankcamp.ui.view.ISortGankView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankFragPresenter extends BasePresenter<ISortGankView> {

    private Context mContext;

    private SortGankModel mModel;

    private List<GankBean> mGankBeanList;

    public SortGankFragPresenter(Context context){
        mGankBeanList = new ArrayList<>();
        mContext  = context;
        mModel = new SortGankModel();
    }

    public void loadGankData(String contentType, int dataCount, int pageCount, final boolean isRefreshData){

        mModel.getSortGankData(contentType,dataCount,pageCount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<SortData, List<GankBean>>() {

                    @Override
                    public List<GankBean> apply(SortData sortData) throws Exception {
                        return sortData.getResults();
                    }
                })
                .subscribe(new Observer<List<GankBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (isRefreshData) {
                            mView.showLoading();
                        }
                    }

                    @Override
                    public void onNext(List<GankBean> value) {
                        if(isRefreshData){
                            mView.refresh(value);
                        }else {
                            if (value.isEmpty()){
                                mView.noMoreData();
                            }else {
                                mView.loadMoreData(value);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refreshData(String contentType,int dataCount, int pageCount){
        loadGankData(contentType,dataCount,pageCount,true);
    }
}
