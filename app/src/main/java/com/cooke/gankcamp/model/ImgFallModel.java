package com.cooke.gankcamp.model;

import com.cooke.gankcamp.Interface.GankService;
import com.cooke.gankcamp.beans.GankData;
import com.cooke.gankcamp.beans.GirlData;

import io.reactivex.Observable;

/**
 * Created by kch on 2017/12/1.
 */

public class ImgFallModel extends BaseModel<GankData> {
    private GankService mService = mRetrofitWrapper.createService(GankService.class);

    public Observable<GirlData> getGirlPicData(int imgCount, int pageIndex){
        return mService.getGirlPicData(imgCount,pageIndex);
    }
}
