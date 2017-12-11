package com.cooke.gankcamp.beans;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by kch on 2017/11/28.
 */

@Entity
public class GankBean implements Serializable,Cloneable {


    private static final long serialVersionUID = -3318311343633027079L;
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

    @Id
    @SerializedName("_id")
    private String objectId;

    private long id;
    private Date createAt;
    private Date publishedAt;
    private String soucre;
    private String type;
    private String url;
    private boolean isUsed;
    @SerializedName("who")
    private String author;

    private String desc;


    private boolean isHeaderTime;

    private boolean isCategory;


    @Generated(hash = 556997774)
    public GankBean(String objectId, long id, Date createAt, Date publishedAt, String soucre, String type, String url,
            boolean isUsed, String author, String desc, boolean isHeaderTime, boolean isCategory) {
        this.objectId = objectId;
        this.id = id;
        this.createAt = createAt;
        this.publishedAt = publishedAt;
        this.soucre = soucre;
        this.type = type;
        this.url = url;
        this.isUsed = isUsed;
        this.author = author;
        this.desc = desc;
        this.isHeaderTime = isHeaderTime;
        this.isCategory = isCategory;
    }

    @Generated(hash = 1453199415)
    public GankBean() {
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean getIsUsed() {
        return this.isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public boolean getIsHeaderTime() {
        return this.isHeaderTime;
    }

    public void setIsHeaderTime(boolean isHeaderTime) {
        this.isHeaderTime = isHeaderTime;
    }

    public boolean getIsCategory() {
        return this.isCategory;
    }

    public void setIsCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }
}
