package com.cooke.gankcamp.Interface;

import com.cooke.gankcamp.beans.GankData;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by kch on 2017/11/28.
 */

public interface GankService {

    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getEveryData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
