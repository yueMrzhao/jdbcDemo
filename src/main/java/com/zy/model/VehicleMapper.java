package com.zy.model;

import com.zy.comm.mapper.BaseMapper;
import com.zy.entity.Vehicle;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
public interface VehicleMapper extends BaseMapper<Long, Vehicle> {

    public List<HashMap<String, Object>> getList();
}
