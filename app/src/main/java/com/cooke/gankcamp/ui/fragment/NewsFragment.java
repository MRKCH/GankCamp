package com.cooke.gankcamp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.presenter.NewsFragPresenter;
import com.cooke.gankcamp.ui.adapter.EverydayGankAdapter;
import com.cooke.gankcamp.ui.view.INewsView;
import com.cooke.gankcamp.util.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by kch on 2017/11/27.
 */

public class NewsFragment extends BaseFragment implements INewsView{

    private NewsFragPresenter mPresenter;

    private EverydayGankAdapter adapter;

    private boolean hasNomoreData;

    private boolean isLoadingMore;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;





    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void init() {
        refresh_layout.setColorSchemeResources(R.color.themeColor,R.color.gray);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_layout.setRefreshing(true);
                mPresenter.refreshData();
            }
        });

        mPresenter = new NewsFragPresenter();
        mPresenter.attachView(this);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);
        adapter = new EverydayGankAdapter(getActivity());
        recycler_view.setAdapter(adapter);
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPos =0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                boolean isBottom = lastVisibleItemPos==adapter.getItemCount()-1;
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&!refresh_layout.isRefreshing()&&isBottom&&!hasNomoreData&&!isLoadingMore){
                    Logger.i("加载更多中...");
                    Calendar c = Calendar.getInstance();
                    c.setTime(mPresenter.getCurDate());
                    c.add(Calendar.DAY_OF_MONTH, -1);// 前一天
                    Date lastDay = c.getTime();

                    isLoadingMore = true;
                    mPresenter.loadGankData(lastDay,false);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPos= layoutManager.findLastCompletelyVisibleItemPosition();
            }
        });

    }

    @Override
    public void showLoading() {
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showLoading(String msg, int progress) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void showErrorMsg(String msg, String content) {

    }



    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void refresh(List<GankBean> list) {
        hasNomoreData = false;
        refresh_layout.setRefreshing(false);
        adapter.refreshData(list);
    }

    @Override
    public void loadMoreData(List<GankBean> list) {

        isLoadingMore = false;
        adapter.loadMoreData(list);
    }

    @Override
    public void noMoreData() {
        hasNomoreData = true;
        adapter.setHasMore(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

}
