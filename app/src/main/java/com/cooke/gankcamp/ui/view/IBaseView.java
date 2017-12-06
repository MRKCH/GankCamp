package com.cooke.gankcamp.ui.view;

/**
 * Created by kch on 2017/11/27.
 */

public interface IBaseView {
    void showLoading();

    void showLoading(String msg);

    void showLoading(String msg, int progress);

    void hideLoading();

    void hideLoading(String msg);

    void showMsg(String msg);

    void showErrorMsg(String msg, String content);



    void close();

    boolean isActive();
}
