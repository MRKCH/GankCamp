package com.cooke.gankcamp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cooke.gankcamp.R;
import com.cooke.gankcamp.beans.GankBean;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kch on 2017/11/28.
 */

public class EverydayGankAdapter extends RecyclerView.Adapter<EverydayGankAdapter.EverydayGankHolder> {

    private List<GankBean> mGankBeanList;
    private Context mContext;

    private boolean isHasMore = true;

    private View mFootView;
    public EverydayGankAdapter(Context context){
        this.mContext = context;
        mGankBeanList = new ArrayList<>();

    }
    @Override
    public EverydayGankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case 0:
                view= LayoutInflater.from(mContext).inflate(R.layout.item_gank_time,parent,false);
                return new TimeHolder(view);
            case 1:
                view= LayoutInflater.from(mContext).inflate(R.layout.item_gank_category,parent,false);
                return new CategoryHolder(view);
            case 3:
                if (mFootView==null)
                mFootView = LayoutInflater.from(mContext).inflate(R.layout.item_foot_view,parent,false);
                return new FooterHolder(mFootView);
            case 2:
            default:
                view= LayoutInflater.from(mContext).inflate(R.layout.item_gank_desc,parent,false);
                return new DescHolder(view);

        }


    }

    public void setHasMore(boolean isHasMore){
        this.isHasMore = isHasMore;
    }

    @Override
    public void onBindViewHolder(EverydayGankHolder holder, int position) {
        if (position==mGankBeanList.size())
            return;
        holder.bindItem(mContext,mGankBeanList.get(position),position);
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
        if (position==mGankBeanList.size()&&mGankBeanList.size()>0){
            return ItemType.FOOTER.ordinal();
        }
        GankBean gankBean = mGankBeanList.get(position);
        if (gankBean.isHeaderTime()){
            return ItemType.PUBLISH_TIME.ordinal();
        }else if (gankBean.isCategory()){
            return ItemType.CATEGORY.ordinal();
        }else
            return ItemType.GANK_DESC.ordinal();

    }


    //下拉刷新，删掉数据，再加载新数据
    public void refreshData(List<GankBean> list){
        if (list.isEmpty())
            return;
        mGankBeanList.clear();
        formatData(list);
        this.notifyDataSetChanged();
    }

    //上拉加载更多。
    public void loadMoreData(List<GankBean> list){
        if (list.isEmpty())
            return;
        formatData(list);
        this.isHasMore=true;
        this.notifyDataSetChanged();

    }

    //格式化数据，使其符合时间，类型，标题的布局特征
    private void formatData(List<GankBean> list){
        //添加时间标题
        GankBean gankBean = new GankBean();
        gankBean.setHeaderTime(true);
        //每次刷新的数据都是同一天，读取第一条数据的时间即可
        gankBean.setPublishedAt(list.get(0).getPublishedAt());
        mGankBeanList.add(gankBean);
        String category = "";
        String lastCategory = "";
        for(int i=0;i<list.size();i++){
            GankBean bean = list.get(i);
            category =bean.getType();
            if (!category.equals(lastCategory)){
                GankBean header = (GankBean) bean.clone();
                header.setCategory(true);
                mGankBeanList.add(header);
            }
            lastCategory = category;
            mGankBeanList.add(bean);
        }

    }
    public static abstract class EverydayGankHolder extends  RecyclerView.ViewHolder{

        public EverydayGankHolder(View itemView) {
            super(itemView);
        }
        public abstract void bindItem(Context context, GankBean gankBean,int position);
    }

    static class TimeHolder extends EverydayGankHolder{
        @BindView(R.id.tv_time)
        TextView tv_time;
        public TimeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindItem(Context context, GankBean gankBean, int position) {
            Date date = gankBean.getPublishedAt();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formatTime = sdf.format(date);
            tv_time.setText(formatTime);
        }
    }

    static class CategoryHolder extends EverydayGankHolder{

        @BindView(R.id.tv_category)
        TextView tv_category;
        public CategoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindItem(Context context, GankBean gankBean, int position) {
            tv_category.setText(gankBean.getType());
        }
    }

    static class DescHolder extends EverydayGankHolder{

        @BindView(R.id.tv_desc)
        TextView tv_desc;
        @BindView(R.id.im_girl)
        ImageView im_girl;
        public DescHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void bindItem(Context context, GankBean gankBean, int position) {
            String type = gankBean.getType();
            if (type.equals("福利")){
                im_girl.setVisibility(View.VISIBLE);
                tv_desc.setVisibility(View.GONE);
                String img_url = gankBean.getUrl();
                Glide.with(context).load(img_url).placeholder(R.mipmap.gem).into(im_girl);
            }else {
                im_girl.setVisibility(View.GONE);
                tv_desc.setVisibility(View.VISIBLE);
                tv_desc.setText(gankBean.getDesc());
            }
        }
    }

     class FooterHolder extends EverydayGankHolder{

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
    public enum ItemType{
        //不要更换枚举的位置
        PUBLISH_TIME,CATEGORY,GANK_DESC,FOOTER;

    }
}
