package com.cooke.gankcamp.Interface;

import com.cooke.gankcamp.beans.GankData;
import com.cooke.gankcamp.beans.GirlData;
import com.cooke.gankcamp.beans.SortData;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;


/**
 * Created by kch on 2017/11/28.
 */

public interface GankService {

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getEveryData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    @GET("data/福利/{imgCount}/{pageIndex}")
    Observable<GirlData> getGirlPicData(@Path("imgCount") int imgCount, @Path("pageIndex") int pageIndex);

    @GET
    Observable<ResponseBody> downloadGirlImage(@Url String url);

    @GET("data/{cotentType}/{dataCount}/{pageIndex}")
    Observable<SortData> getSortGankData(@Path("contentType") String contentType
            ,@Path("dataCount") int dataCount, @Path("pageIndex") int pageIndex);
}
