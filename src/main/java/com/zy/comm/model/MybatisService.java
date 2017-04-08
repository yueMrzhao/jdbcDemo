package com.zy.comm.model;

import com.zy.entity.Vehicle;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zy on 2017/4/2.
 */
@Service
public class MybatisService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public <Vehicle> List<Vehicle> getList(String statement){
        return sqlSessionTemplate.<Vehicle>selectList(statement);
    }
}
