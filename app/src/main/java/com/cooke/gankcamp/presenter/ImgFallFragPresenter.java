package com.cooke.gankcamp.presenter;

import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.GankData;
import com.cooke.gankcamp.beans.Girl;
import com.cooke.gankcamp.beans.GirlData;
import com.cooke.gankcamp.model.ImgFallModel;
import com.cooke.gankcamp.ui.view.IImgFallView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kch on 2017/12/1.
 */

public class ImgFallFragPresenter extends BasePresenter<IImgFallView> {

    private static final String TAG = "ImgFallFragPresenter";

    private List<Girl> mDataList;

    private ImgFallModel mModel;

    public ImgFallFragPresenter(){
        mModel = new ImgFallModel();
        mDataList = new ArrayList<>();
        Logger.init(TAG);
    }

    public void refreshData(int imgCount, int pageCount){
        loadGirlData(imgCount,pageCount,true);
    }

    public void loadGirlData(int imgCount, int pageCount, final boolean isRefreshData){
        mModel.getGirlPicData(imgCount,pageCount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<GirlData, List<Girl>>() {

                    @Override
                    public List<Girl> apply(GirlData girlData) throws Exception {
                        return girlData.results;
                    }
                })
                .subscribe(new Observer<List<Girl>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Girl> value) {
                        mDataList.clear();
                        mDataList.addAll(value);
                        if (isRefreshData){
                            mView.refresh(mDataList);
                        }else {
                            if (mDataList.size()==0){
                                mView.noMoreData();
                            }else {
                                mView.loadMoreData(mDataList);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage().toString());
                    }

                    @Override
                    public void onComplete() {
                        Logger.d("onComplete");
                    }
                });
    }
}
