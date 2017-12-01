package com.cooke.gankcamp.model;

import com.cooke.gankcamp.Interface.GankService;
import com.cooke.gankcamp.beans.GankData;

import io.reactivex.Observable;


/**
 * Created by kch on 2017/11/28.
 */

public class NewsModel extends BaseModel<GankData> {
    private GankService mService = mRetrofitWrapper.createService(GankService.class);


    public Observable<GankData> loadEveryDayData(int year, int month, int day) {
        return mService.getEveryData(year,month,day);
    }
}
