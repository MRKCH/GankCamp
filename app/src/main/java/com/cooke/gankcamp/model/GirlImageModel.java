package com.cooke.gankcamp.model;

import com.cooke.gankcamp.Interface.GankService;


import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by kch on 2017/12/6.
 */

public class GirlImageModel extends BaseModel<ResponseBody>{
    GankService mService = mRetrofitWrapper.createService(GankService.class);


    public Observable<okhttp3.ResponseBody> downloadGirlImage(String url){
        return mService.downloadGirlImage(url);
    }
}
