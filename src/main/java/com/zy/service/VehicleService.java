package com.zy.service;

import com.zy.comm.service.BaseService;
import com.zy.entity.Vehicle;
import com.zy.model.VehicleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
@Service
public class VehicleService extends BaseService<Long, Vehicle, VehicleMapper> {

    @Autowired
    private VehicleMapper mapper;

    public List<HashMap<String, Object>> getList(){
        return mapper.getList();
    }
}
