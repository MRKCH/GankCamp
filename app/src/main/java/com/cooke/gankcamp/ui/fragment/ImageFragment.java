package com.cooke.gankcamp.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.Girl;
import com.cooke.gankcamp.presenter.ImgFallFragPresenter;
import com.cooke.gankcamp.ui.activity.ImageActivity;
import com.cooke.gankcamp.ui.adapter.ImgFallAdapter;
import com.cooke.gankcamp.ui.view.IImgFallView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by kch on 2017/11/27.
 */

public class ImageFragment extends BaseFragment implements IImgFallView ,ImgFallAdapter.OnImgClickListener{

    private static final int PER_PAGE_COUNT = 9;

    private ImgFallFragPresenter mPresenter;
    private ImgFallAdapter adapter;

    private int[] lastPositions;

    private int lastVisibleItemPosition;

    private boolean isLoadingMore;

    private boolean hasNomoreData;

    private int pageIndex = 1;

    private boolean isFistLoad = true;//是否第一次加载数据

    private boolean mIsVisibleToUser = true;//上一次fragment是否隐藏


    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_image;
    }

    @Override
    public void init() {
        refresh_layout.setColorSchemeResources(R.color.themeColor,R.color.gray);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_layout.setRefreshing(true);
                mPresenter.refreshData(PER_PAGE_COUNT,1);
            }
        });

        mPresenter = new ImgFallFragPresenter();
        mPresenter.attachView(this);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(layoutManager);
        adapter = new ImgFallAdapter(getActivity());
        adapter.setImgClickListener(this);
        recycler_view.setAdapter(adapter);
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boolean isBottom = lastVisibleItemPosition>=adapter.getItemCount() - 1;
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&!refresh_layout.isRefreshing()&&isBottom&&!hasNomoreData&&!isLoadingMore){
                    isLoadingMore = true;
                    pageIndex++;
                    mPresenter.loadGirlData(PER_PAGE_COUNT,pageIndex,false);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (lastPositions == null) {
                    lastPositions = new int[layoutManager.getSpanCount()];
                }
                layoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
            }
        });
    }


    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void showLoading() {

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
    public void refresh(List<Girl> list) {
        pageIndex=1;
        refresh_layout.setRefreshing(false);
        adapter.refreshData(list);
        isFistLoad = false;
    }

    @Override
    public void loadMoreData(List<Girl> list) {
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
    public void onImgClick(Girl girl, View view) {

        Intent intent = new Intent(getActivity(), ImageActivity.class);
        String url = girl.getUrl();
        intent.putExtra(ImageActivity.IMG_URL,url);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), view, "image").toBundle());
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!mIsVisibleToUser&&isVisibleToUser&&isFistLoad){
            refresh_layout.setRefreshing(true);
            mPresenter.refreshData(PER_PAGE_COUNT,1);
        }
        mIsVisibleToUser = isVisibleToUser;
    }
}
