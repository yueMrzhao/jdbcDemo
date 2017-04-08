package com.zy.comm.entity;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by zy on 2017/3/31.
 */
public class StringBaseEntity<T> extends BaseEntity<T, String> {

    private static final long serialVersionUID = 1L;
    @Override
    public void preInsert(){
     /*   if(StringUtils.isEmpty(id)){

        }*/
    }

}
