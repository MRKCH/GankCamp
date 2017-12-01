package com.cooke.gankcamp.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kch on 2017/11/28.
 */

public class GankBean implements Serializable,Cloneable {

    /**
     * _id: "5a1627fe421aa90fe2f02c80",
     * createdAt: "2017-11-23T09:44:30.245Z",
     * desc: "适配三星Galaxy S8及S8+",
     * publishedAt: "2017-11-24T11:08:03.624Z",
     * source: "web",
     * type: "Android",
     * url: "https://mp.weixin.qq.com/s?__biz=MzIwMzYwMTk1NA==&mid=2247488324&idx=1&sn=e043220e37d6eaaff217769f4061c04e",
     * used: true,
     * who: "陈宇明"
     */
    private String id;
    private Date createAt;
    private Date publishedAt;
    private String soucre;
    private String type;
    private String url;
    private boolean isUsed;
    private String author;
    private String desc;

    private boolean isHeaderTime;

    private boolean isCategory;


    public boolean isCategory() {
        return isCategory;
    }

    public void setCategory(boolean category) {
        isCategory = category;
    }

    public boolean isHeaderTime() {
        return isHeaderTime;
    }

    public void setHeaderTime(boolean headerTime) {
        isHeaderTime = headerTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSoucre() {
        return soucre;
    }

    public void setSoucre(String soucre) {
        this.soucre = soucre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Object clone()  {
        GankBean gankBean=null;
        try {
            gankBean = (GankBean) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }

        return gankBean;
    }
}
