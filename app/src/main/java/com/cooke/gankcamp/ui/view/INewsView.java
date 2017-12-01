package com.cooke.gankcamp.ui.view;

import com.cooke.gankcamp.beans.GankBean;

import java.util.List;

/**
 * Created by kch on 2017/11/28.
 */

public interface INewsView extends IBaseView{
    public void refresh(List<GankBean> list);
    public void loadMoreData(List<GankBean> list);
    public void noMoreData();

}
