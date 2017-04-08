package com.zy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zy.comm.entity.Page;
import com.zy.comm.model.JedisTemplate;
import com.zy.comm.model.MybatisService;
import com.zy.entity.Vehicle;
import com.zy.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
@Controller
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private MybatisService mybatisService;

    @Autowired
    private JedisTemplate jedisTemplate;

    @RequestMapping("/getList/{pageNum}/{pageSize}")
    @ResponseBody
    public String getList(@PathVariable int pageNum, @PathVariable int pageSize){
        Vehicle vehicle = new Vehicle();
        if(vehicle.getPage()==null){
            Page<Vehicle> page = new Page<>();
            vehicle.setPage(page);
        }

        Page<Vehicle> page = vehicle.getPage();
        page.setPageSize(pageSize);
        page.setPageNo(pageNum);
        Page<Vehicle> list = vehicleService.getPage(vehicle);
        String result = JSON.toJSONString(list);
        return result;
    }


    @RequestMapping("/getLists")
    @ResponseBody
    public String getLists(){

        List<Vehicle> list = mybatisService.getList("com.zy.model.VehicleMapper.selectSelective");

        String result = JSON.toJSONString(list);
        return result;
    }

    @RequestMapping("/testRedis/{key}")
    @ResponseBody
    public String getRedisValue(@PathVariable String key){
        String ss = jedisTemplate.getKeyValue(key);
        return ss;
    }



}
