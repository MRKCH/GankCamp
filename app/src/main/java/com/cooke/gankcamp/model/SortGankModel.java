package com.cooke.gankcamp.model;

import com.cooke.gankcamp.Interface.GankService;
import com.cooke.gankcamp.beans.SortData;

import io.reactivex.Observable;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankModel extends BaseModel<SortData> {

    private GankService mService = mRetrofitWrapper.createService(GankService.class);

    public Observable<SortData> getSortGankData(String contentType
            , int dataCount,int pageIndex){
        return mService.getSortGankData(contentType,dataCount,pageIndex);
    }
}
