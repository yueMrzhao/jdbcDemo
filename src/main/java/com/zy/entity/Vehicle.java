package com.zy.entity;

import com.zy.comm.entity.BaseEntity;
import com.zy.comm.entity.LongBaseEntity;

/**
 * Created by zy on 2017/3/31.
 */
public class Vehicle extends LongBaseEntity<Vehicle> {

    private String vin;
    private Byte vehicle_status;
    private byte bind_status;

}
