package com.cooke.gankcamp.presenter;

import com.cooke.gankcamp.ui.view.IBaseView;

/**
 * Created by kch on 2017/11/27.
 */

public interface IPresenter<T extends IBaseView>{

        void attachView(T view);


        void detachView();


        void subscribe();


        void unsubscribe();
}
