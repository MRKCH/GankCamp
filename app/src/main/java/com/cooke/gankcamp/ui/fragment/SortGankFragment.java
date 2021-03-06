package com.cooke.gankcamp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.contsants.ContentType;
import com.cooke.gankcamp.presenter.SortGankFragPresenter;
import com.cooke.gankcamp.ui.activity.WebGankActivity;
import com.cooke.gankcamp.ui.adapter.SortGankAdapter;
import com.cooke.gankcamp.ui.adapter.SortGankFragAdapter;
import com.cooke.gankcamp.ui.view.ISortGankView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankFragment extends BaseFragment implements ISortGankView,SortGankAdapter.OnItemClickListener {

    private static final String CONTENT_TYPE = "content_type";

    private static final int DATA_COUNT = 20;

    private int pageIndex = 1;

    private SortGankFragPresenter mPresenter;

    private SortGankAdapter adapter;

    private boolean isLoaded;

    private boolean hasNomoreData;

    private boolean isLoadingMore;

    private String contentType;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_sort_gank;
    }

    public static SortGankFragment getInstance(@NonNull String contentType){
        SortGankFragment mInstance = new SortGankFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_TYPE,contentType);
        mInstance.setArguments(bundle);
        return mInstance;

    }




    @Override
    public void init() {
        contentType = getArguments().getString(CONTENT_TYPE);


        mPresenter = new SortGankFragPresenter(getActivity());
        mPresenter.attachView(this);

        refresh_layout.setColorSchemeResources(R.color.themeColor,R.color.gray);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pageIndex=1;
                mPresenter.refreshData(contentType,DATA_COUNT,pageIndex);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(layoutManager);
        adapter = new SortGankAdapter(getActivity());
        adapter.setOnItemClickListener(this);
        recycler_view.setAdapter(adapter);

        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItemPos = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                boolean isBottom = lastVisibleItemPos == adapter.getItemCount() - 1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !refresh_layout.isRefreshing() && isBottom && !hasNomoreData && !isLoadingMore) {

                    pageIndex++;
                    mPresenter.loadGankData(contentType, DATA_COUNT,pageIndex,false);

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPos = layoutManager.findLastCompletelyVisibleItemPosition();
            }

        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mPresenter.refreshData(contentType,DATA_COUNT,pageIndex);
                refresh_layout.setRefreshing(true);
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
    public void hideLoading(String msg) {

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
        isLoaded = true;
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


    @Override
    public void onItemClick(View view, GankBean gankBean) {
        String url = gankBean.getUrl();
        String title = gankBean.getDesc();
        Intent intent = new Intent(getActivity(), WebGankActivity.class);
        intent.putExtra(WebGankActivity.WEB_URL,url);
        intent.putExtra(WebGankActivity.WEB_TITLE,title);
        startActivity(intent);
    }
}
