package com.cooke.gankcamp.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kch on 2017/11/28.
 */

public class GankData {

    public boolean isError;
    public List<String> category;
    public Results results;
    public class Results {
        @SerializedName("Android")
        public List<GankBean> androidList;
        @SerializedName("iOS")
        public List<GankBean> iosList;
        @SerializedName("休息视频")
        public List<GankBean> videoList;
        @SerializedName("拓展资源")
        public List<GankBean> resourceList;
        @SerializedName("瞎推荐")
        public List<GankBean> recommendList;
        @SerializedName("福利")
        public List<GankBean> welfareList;

        @SerializedName("App")
        public List<GankBean> appList;
    }



}
