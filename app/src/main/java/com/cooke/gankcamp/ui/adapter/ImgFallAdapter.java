package com.cooke.gankcamp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;
import com.cooke.gankcamp.beans.Girl;
import com.cooke.gankcamp.util.ScreenUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kch on 2017/12/1.
 */

public class ImgFallAdapter extends RecyclerView.Adapter<ImgFallAdapter.BaseHolder> {

    private Context mContext;

    private List<Girl> mDataList;



    private boolean isHasMore = true;


    private OnImgClickListener listener;

    public ImgFallAdapter(Context context){
        this.mContext = context;
        mDataList = new ArrayList<>();
    }
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
           View  footView = LayoutInflater.from(mContext).inflate(R.layout.item_foot_view,parent,false);
            return new FooterHolder(footView);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_img_fall, parent, false);
            return new ImgFallHolder(view,mContext);
        }
    }

    @Override
    public void onBindViewHolder(final BaseHolder holder, int position) {

        if (holder instanceof ImgFallHolder){
            final Girl girl = mDataList.get(position);
            String url = girl.getUrl();
            Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate().placeholder(R.color.white).into(((ImgFallHolder)holder).img_girl);
            ((ImgFallHolder)holder).img_girl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onImgClick(girl,((ImgFallHolder)holder).img_girl);
                    }
                }
            });


        }else if (holder instanceof FooterHolder){
            if (isHasMore){
                ((FooterHolder)holder).tv_tips.setText("加载中...");
            }else{
                ((FooterHolder)holder).tv_tips.setText("没有更多了...");
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList.size()>0) {
            return mDataList.size() + 1;
        }else {
            return mDataList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        int count = getItemCount();

        if (position==count-1&&count>0)
            return 1;
        else
            return 0;
    }

    public void setHasMore(boolean hasMore){
        this.isHasMore = hasMore;
    }

    //下拉刷新，删掉数据，再加载新数据
    public void refreshData(List<Girl> list){
        if (list.isEmpty())
            return;
        mDataList.clear();
        mDataList.addAll(list);
        this.notifyDataSetChanged();
    }

    //上拉加载更多。
    public void loadMoreData(List<Girl> list){
        if (list.isEmpty())
            return;
        this.isHasMore=true;
        mDataList.addAll(list);
        this.notifyDataSetChanged();

    }


    private static   int getRandomHeight(int width){
        Random random = new Random();
        double ratio = random.nextDouble()*0.5+1;
        return (int) (width*ratio);

    }
    static class ImgFallHolder extends BaseHolder{

        @BindView(R.id.img_girl)
        ImageView img_girl;

        @BindView(R.id.card_view)
        CardView card_view;
        public ImgFallHolder(View itemView,Context context) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            int screenWidth = ScreenUtil.getScreenWidth(context);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) card_view.getLayoutParams();
            layoutParams.width = (screenWidth-layoutParams.leftMargin*2)/2;
            layoutParams.height = getRandomHeight(layoutParams.width);
            card_view.setLayoutParams(layoutParams);

        }

    }

    static class FooterHolder extends BaseHolder{

        @BindView(R.id.tv_tips)
        TextView tv_tips;
        public FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }

    static  class BaseHolder extends RecyclerView.ViewHolder{

        public BaseHolder(View itemView) {
            super(itemView);
        }

    }

    public void setImgClickListener(OnImgClickListener listener){
        this.listener = listener;
    }
    public  interface  OnImgClickListener{
        public void onImgClick(Girl girl,View view);
    }

    @Override
    public void onViewAttachedToWindow(BaseHolder holder) {
        super.onViewAttachedToWindow(holder);

        if (holder instanceof FooterHolder){
            Logger.i("onViewAttachedToWindow");
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
            ((FooterHolder) holder).itemView.setLayoutParams(p);
        }
    }

}
