package com.zy.comm.entity;

import java.io.Serializable;

/**
 * Created by zy on 2017/3/31.
 */
public abstract class BaseEntity<K,T> implements Serializable{

    private static final long serialVersionUid = 1L;

    public Page<T> page;
    public K id;

    public abstract void preInsert();

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }
}
