package com.cooke.gankcamp.ui.view;

import com.cooke.gankcamp.beans.GankBean;

import java.util.List;

/**
 * Created by kch on 2017/12/1.
 */

public interface ILoadingView<T> extends IBaseView{
    public void refresh(List<T> list);
    public void loadMoreData(List<T> list);
    public void noMoreData();
}
