package com.cooke.gankcamp.presenter;

import com.cooke.gankcamp.ui.view.IBaseView;

/**
 * Created by kch on 2017/11/27.
 */

public abstract class BasePresenter<T extends IBaseView> implements IPresenter<T>{
    protected T mView;
    public T getView() {
        return mView;
    }


    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        unsubscribe();
        this.mView = null;
    }

    @Override
    public void unsubscribe(){

    }

    @Override
    public void subscribe(){

    }

    public boolean isViewAttached() {
        return mView != null;
    }
}
