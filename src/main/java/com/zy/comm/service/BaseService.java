package com.zy.comm.service;

import com.zy.comm.entity.BaseEntity;
import com.zy.comm.entity.Page;
import com.zy.comm.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
public abstract class BaseService<K, T extends BaseEntity<K,T>, Mapper extends BaseMapper<K,T>> {

    @Autowired
    Mapper mapper;

    @Transactional(readOnly=false)
    public int insert(T t){
        if(t==null){
            return 0;
        }

        t.preInsert();
        return this.mapper.insert(t);
    }

    @Transactional(readOnly=false)
    public int insertSelective(T t){
        if(t==null){
            return 0;
        }

        return this.mapper.insertSelective(t);
    }

    @Transactional(readOnly=false)
    public int deleteByPrimaryKey(K id){
        if(id==null){
            return 0;
        }

        return this.mapper.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly=false)
    public int updateSelective(T t){
        if(t==null){
            return 0;
        }

        return this.mapper.updateSelective(t);
    }

    @Transactional(readOnly=false)
    public int updateByPrimaryKey(K id){
        if(id instanceof Integer){
            if(((Integer) id).intValue()==0){
                return 0;
            }
        }
        if(id==null){
            return 0;
        }

        return this.mapper.updateByPrimaryKey(id);
    }

    public T selectOne(T t){
       if(t==null){
           return null;
       }

        return this.mapper.selectSimpleSelective(t);
    }

    public T selectOne(K id){
        if(id==null){
            return null;
        }

        return this.mapper.selectSimpleByPrimary(id);
    }

    public List<T> getList(T t){
        if(t!=null){
            return null;
        }

        return this.mapper.selectSelective(t);
    }

    public List<T> getList(K id){
        if(id!=null){
            return null;
        }

        return this.mapper.selectByPrimaryKey(id);
    }

    public Page<T> getPage(T t){
        if(t==null){
            return null;
        }

        if(t.getPage()==null){
            Page<T> page = new Page<>();
            t.setPage(page);
        }

        List<T> list = this.mapper.selectSelective(t);
        if(list==null){
            return null;
        }

        Page<T> page = t.getPage();
        page.setRecord(list);
        return page;
    }




}
