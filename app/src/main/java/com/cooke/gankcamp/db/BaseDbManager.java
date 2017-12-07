package com.cooke.gankcamp.db;

import java.util.List;

/**
 * Created by kch on 2017/12/7.
 */

public abstract class BaseDbManager<T> {

    public abstract void insert(T bean);
    public abstract void update(T bean);
    public abstract void insertList(List<T> beans);
    public abstract void delete(T bean);
    public abstract void deleteAll();
    public abstract List<T> queryList();


}
