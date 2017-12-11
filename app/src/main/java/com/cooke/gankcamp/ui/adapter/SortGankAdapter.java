package com.cooke.gankcamp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kch on 2017/12/7.
 */

public class SortGankAdapter extends RecyclerView.Adapter<SortGankAdapter.BaseHolder> {

    private Context mContext;

    private List<GankBean> mGankBeanList;

    private boolean isHasMore = true;

    public SortGankAdapter(Context context){
        this.mContext = context;
        mGankBeanList = new ArrayList<>();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_sort_gank, parent, false);
            return new SortGankHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot_view,parent,false);
            return new FooterHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        if (position==mGankBeanList.size())
            return;
        GankBean gankBean = mGankBeanList.get(position);
        holder.bindItem(mContext,gankBean,position);
    }



    @Override
    public int getItemCount() {
        if (mGankBeanList.size()>0) {
            return mGankBeanList.size() + 1;
        }else {
            return mGankBeanList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return 1;
        }else {
            return 0;
        }
    }
    abstract class BaseHolder extends RecyclerView.ViewHolder{

        public BaseHolder(View itemView) {
            super(itemView);
        }
        public abstract void bindItem(Context context, GankBean gankBean,int position);
    }

     class SortGankHolder extends BaseHolder{

        @BindView(R.id.tv_author)
        TextView tv_author;
        @BindView(R.id.tv_title)
        TextView tv_title;
         @BindView(R.id.card_item)
         CardView card_item;

        public SortGankHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindItem(Context context, final GankBean gankBean, final int position) {
            String author = gankBean.getAuthor();

            card_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null)
                        listener.onItemClick(card_item,gankBean);
                }
            });
            if (!TextUtils.isEmpty(author)){
                tv_author.setText(gankBean.getAuthor());
            }else {
                tv_author.setText("null");
            }
            tv_title.setText(gankBean.getDesc());
        }
    }

     class FooterHolder extends BaseHolder{

        @BindView(R.id.tv_tips)
        TextView tv_tips;
        public FooterHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bindItem(Context context, GankBean gankBean, int position) {
            if (isHasMore){
                tv_tips.setText("加载中...");
            }else{
                tv_tips.setText("没有更多数据了");
            }
        }

    }

    //下拉刷新，删掉数据，再加载新数据
    public void refreshData(List<GankBean> list){
        if (list.isEmpty())
            return;
        mGankBeanList.clear();
        mGankBeanList.addAll(list);
        this.notifyDataSetChanged();
    }

    //上拉加载更多。
    public void loadMoreData(List<GankBean> list){
        if (list.isEmpty())
            return;
        mGankBeanList.addAll(list);
        this.isHasMore=true;
        this.notifyDataSetChanged();

    }

    public void setHasMore(boolean isHasMore){
        this.isHasMore = isHasMore;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public interface OnItemClickListener{
        public void onItemClick(View view,GankBean gankBean);
    }
}
