package com.cooke.gankcamp.presenter;

import android.app.Fragment;
import android.content.Context;

import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.GankData;
import com.cooke.gankcamp.model.NewsModel;
import com.cooke.gankcamp.ui.view.INewsView;
import com.cooke.gankcamp.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by kch on 2017/11/28.
 */

public class NewsFragPresenter extends BasePresenter<INewsView> {

    private static final String TAG = "NewsFragPresenter";

    //一天总共的毫秒数
    private static final int DAY_OF_MILLSECOND = 24*60*60*1000;

    private int emptyDayCount = 0;
    public void refreshData() {
        Date date = new Date(System.currentTimeMillis());
        loadGankData(date,true);
    }

    private NewsModel mNewsModel;

    private List<GankBean> mGankDataList;

    private Date mCurDate;//当前请求的时间


    private static final  int MAX_DAY_INTERVAL = 15;



    public NewsFragPresenter() {
        mNewsModel = new NewsModel();
        mGankDataList = new ArrayList<>();
        com.orhanobut.logger.Logger.init(TAG);
    }


    public Date getCurDate(){
        return mCurDate;
    }


    public void loadGankData(final Date date,  final boolean isRefreshData) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        com.orhanobut.logger.Logger.d("month:"+month+",day:"+day);

        mNewsModel.loadEveryDayData(year, month, day)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<GankData, GankData.Results>() {
                    @Override
                    public GankData.Results apply(GankData gankData) throws Exception {
                        return gankData.results;
                    }
                })
                .map(new Function<GankData.Results, List<GankBean>>() {
                    @Override
                    public List<GankBean> apply(GankData.Results results) throws Exception {
                        return getAllData(results);
                    }
                })
                .subscribe(new Observer<List<GankBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GankBean> value) {

                        com.orhanobut.logger.Logger.d("onNext:"+"==== isRefresh:"+isRefreshData);
                        mCurDate = date;
                        //下拉刷新
                        if (isRefreshData) {
                            emptyDayCount=0;
                            if (value.isEmpty()){
                                loadGankData(new Date(date.getTime()-DAY_OF_MILLSECOND),true);
                            }else {
                                mView.refresh(mGankDataList);

                            }

                        } else {

                            if (value.isEmpty()){
                                emptyDayCount++;
                                //请求超过次数，视为没有数据了
                                if (emptyDayCount>MAX_DAY_INTERVAL){
                                    mView.noMoreData();
                                }else{
                                    loadGankData(new Date(date.getTime()-DAY_OF_MILLSECOND),false);
                                }
                            }else {
                                emptyDayCount=0;
                                mView.loadMoreData(mGankDataList);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        com.orhanobut.logger.Logger.d("onComplete:"+"=== isRefresh:"+isRefreshData);

                    }
                });


    }

    private List<GankBean> getAllData(GankData.Results results) {

        mGankDataList.clear();

        if (results.welfareList != null) {
            mGankDataList.addAll(results.welfareList);
        }

        if (results.androidList != null) {
            mGankDataList.addAll(results.androidList);
        }
        if (results.iosList != null) {
            mGankDataList.addAll(results.videoList);
        }
        if (results.resourceList != null) {
            mGankDataList.addAll(results.resourceList);
        }
        if (results.recommendList != null) {
            mGankDataList.addAll(results.recommendList);
        }

        return mGankDataList;


    }
}
