package com.work.service;

import com.github.pagehelper.Page;
import com.work.model.Car;
import com.work.model.Location;

import java.util.List;
import java.util.Map;

/**
 * Created by 稻草人 on 2019/4/9.
 */

public interface LocationService {

    List<Location> findLocationList();

    /**
     * 查询停车点
     * @return
     */
    Page<Location> findLocationByPage(Integer page,Integer size);

    /**
     * 条件搜索停车点
     * @param location
     * @return
     */
    Page<Location> searchLocationByPage(Location location,Integer page,Integer size);

    /**
     * 根据id查询停车点
     * @param locationId
     * @return
     */
    Location findLocationById(Integer locationId);

    /**
     * 修改停车点
     * @param location
     * @return
     */
    public int updateLocation(Location location);

    /**
     * 添加停车点
     * @param location
     * @return
     */
    int addLocation(Location location);


    /**
     * 插入 停车点和车辆中间表
     * @param params
     * @return
     */
    int addLocationCar(Map<String,Object> params);

    /**
     * 查找 未加入停车点的车辆
     * @return
     */
    List<Car> findNoLocationCar();

    /**
     * 修改车辆状态
     * @param car
     * @return
     */
    int updateCarStatus(Car car);

    /**
     * 删除停车网点
     * @param locationId
     * @return
     */
    int deleteLocation(Integer locationId);

    /**
     * 从停车点移除指定车辆
     * @param params
     * @return
     */
    int removeLocationCar(Map<String,Object> params);


    Location findLocationByName(String locationName);

}
