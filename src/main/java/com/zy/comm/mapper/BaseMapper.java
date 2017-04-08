package com.zy.comm.mapper;

import com.zy.comm.entity.BaseEntity;

import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
public interface BaseMapper<K,T extends BaseEntity<K,T>> {

    int insert(T t);
    int insertSelective(T t);
    int deleteByPrimaryKey(K id);
    int updateSelective(T t);
    int updateByPrimaryKey(K id);
    T selectSimpleSelective(T t);
    T selectSimpleByPrimary(K id);
    List<T> selectSelective(T t);
    List<T> selectByPrimaryKey(K id);
}
