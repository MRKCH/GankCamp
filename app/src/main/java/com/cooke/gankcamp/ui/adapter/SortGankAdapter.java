package com.cooke.gankcamp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.cooke.gankcamp.beans.GankBean;

import java.util.List;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankAdapter extends RecyclerView.Adapter<SortGankAdapter.SortGankHolder> {

    private Context mContext;

    private List<GankBean> mGankBeanList;

    private boolean isHasMore = true;

    public SortGankAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public SortGankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SortGankHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class SortGankHolder extends RecyclerView.ViewHolder{

        public SortGankHolder(View itemView) {
            super(itemView);
        }
    }

    //下拉刷新，删掉数据，再加载新数据
    public void refreshData(List<GankBean> list){
        if (list.isEmpty())
            return;
        mGankBeanList.clear();
        this.notifyDataSetChanged();
    }

    //上拉加载更多。
    public void loadMoreData(List<GankBean> list){
        if (list.isEmpty())
            return;

        this.isHasMore=true;
        this.notifyDataSetChanged();

    }

    public void setHasMore(boolean isHasMore){
        this.isHasMore = isHasMore;
    }
}
