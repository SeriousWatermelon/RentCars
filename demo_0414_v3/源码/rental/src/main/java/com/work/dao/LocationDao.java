package com.work.dao;

import com.github.pagehelper.Page;
import com.work.model.Car;
import com.work.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * Created by 稻草人 on 2019/4/8.
 */
@Repository
public interface LocationDao {

    /**
     * 查询所有停车点
     */
    List<Location> findLocationList();

    /**
     * 查询停车点
     * @return
     */
    Page<Location> findLocationByPage();

    /**
     * 条件搜索停车点
     * @param location
     * @return
     */
    Page<Location> searchLocationByPage(Location location);

    /**
     * 根据id查询停车点
     * @param locationId
     * @return
     */
    Location findLocationById(Integer locationId);

    /**
     * 停车点更新
     * @param location
     * @return
     */
    int updateLocation(Location location);

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
     * 更新 停车点和车辆中间表
     * @param params
     * @return
     */
    int updateLocationCar(Map<String,Object> params);

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
     * 删除停车网点关联的所有车辆
     * @param locationId
     * @return
     */
    int deleteLocationCar(Integer locationId);

    /**
     * 从停车点移除指定车辆
     * @param params
     * @return
     */
    int removeLocationCar(Map<String,Object> params);


    /**
     * 根据地址id查找关联的车辆
     */
    List<Car> findCarsByLocationId(Integer locationId);

    /**
     * 根据地点的id地址，修改车辆的状态为 未编入停车点车辆
     * @param locationId
     * @return
     */
    int dropCarFromLocation(Integer locationId);


    /**
     * 根据停车点名称查找停车点
     * @return
     */
    Location findLocationByName(String locationName);
}
