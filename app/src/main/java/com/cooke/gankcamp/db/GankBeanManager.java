package com.cooke.gankcamp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cooke.gankcamp.beans.GankBean;
import com.speedystone.greendaodemo.db.DaoMaster;
import com.speedystone.greendaodemo.db.DaoSession;
import com.speedystone.greendaodemo.db.GankBeanDao;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by kch on 2017/12/7.
 */

public class GankBeanManager extends BaseDbManager<GankBean> {

    private static volatile GankBeanManager mInstance;
    private final static String dbName = "gank.db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    private GankBeanManager(){

    }



    private GankBeanManager(Context context){
        this.context =context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);

    }

    public static GankBeanManager getInstance(Context context) {
        if (mInstance==null){
            synchronized (GankBeanManager.class){
                if (mInstance==null)
                    mInstance = new GankBeanManager(context);
            }
        }
        return mInstance;
    }


    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    private GankBeanDao getWritableGankBeanDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankBeanDao gankBeanDao = daoSession.getGankBeanDao();

        return gankBeanDao;
    }

    private GankBeanDao geReadableGankBeanDao(){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        GankBeanDao gankBeanDao = daoSession.getGankBeanDao();
        return gankBeanDao;
    }


    @Override
    public void insert(GankBean bean) {
        GankBeanDao gankBeanDao = getWritableGankBeanDao();
        gankBeanDao.insert(bean);
    }

    @Override
    public void update(GankBean bean) {
        GankBeanDao gankBeanDao = getWritableGankBeanDao();
        gankBeanDao.update(bean);
    }

    @Override
    public void insertList(List<GankBean> beans) {
        if (beans==null||beans.isEmpty())
            return;
        GankBeanDao gankBeanDao =getWritableGankBeanDao();
        gankBeanDao.insertInTx(beans);
    }

    @Override
    public void delete(GankBean bean) {
        GankBeanDao gankBeanDao = getWritableGankBeanDao();
        gankBeanDao.delete(bean);
    }

    @Override
    public void deleteAll() {
        GankBeanDao gankBeanDao = getWritableGankBeanDao();

        gankBeanDao.deleteAll();
    }

    public Date queryDate(){
        GankBeanDao gankBeanDao =geReadableGankBeanDao();
        QueryBuilder qb = gankBeanDao.queryBuilder();
        qb.limit(1);
        List<GankBean> list = qb.list();
        Date date;
        if (!list.isEmpty()){
            date = list.get(0).getPublishedAt();
        }else {
            date = new Date(System.currentTimeMillis());
        }
        return date;
    }

    @Override
    public List<GankBean> queryList() {
        GankBeanDao gankBeanDao =geReadableGankBeanDao();
        QueryBuilder<GankBean> qb = gankBeanDao.queryBuilder();
        List<GankBean> list = qb.list();
        return list;
    }
}
