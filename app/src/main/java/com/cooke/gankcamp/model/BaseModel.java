package com.cooke.gankcamp.model;

import com.cooke.gankcamp.net.RetrofitWrapper;

import java.util.Map;



/**
 * Created by kch on 2017/11/28.
 */

public abstract class BaseModel<T> implements IModel{
    protected RetrofitWrapper mRetrofitWrapper = new RetrofitWrapper();



}
