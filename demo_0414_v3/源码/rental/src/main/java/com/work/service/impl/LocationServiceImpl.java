package com.work.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.work.dao.LocationDao;
import com.work.model.Car;
import com.work.model.Location;
import com.work.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 稻草人 on 2019/4/9.
 */

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<Location> findLocationList() {
        return locationDao.findLocationList();
    }

    @Override
    public Page<Location> findLocationByPage(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return locationDao.findLocationByPage();
    }

    @Override
    public Page<Location> searchLocationByPage(Location location,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return locationDao.searchLocationByPage(location);
    }

    @Override
    public Location findLocationById(Integer locationId) {
        return locationDao.findLocationById(locationId);
    }

    @Transactional
    @Override
    public int updateLocation(Location location){
        return locationDao.updateLocation(location);
    }

    @Transactional
    @Override
    public int addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Transactional
    @Override
    public int addLocationCar(Map<String, Object> params) {
        return locationDao.addLocationCar(params);
    }

    @Override
    public List<Car> findNoLocationCar() {
        return locationDao.findNoLocationCar();
    }

    @Transactional
    @Override
    public int updateCarStatus(Car car) {
        return locationDao.updateCarStatus(car);
    }

    @Transactional
    @Override
    public int deleteLocation(Integer locationId) {
        int j = 0; int i = 0;
        //1. 修改车辆状态为 未加入停车点
        int h = locationDao.dropCarFromLocation(locationId);
        if(h>0){//2. 删除停车点和车辆的中间表
            j = locationDao.deleteLocationCar(locationId);
        }
        if(j>0){//3. 删除停车点
            i = locationDao.deleteLocation(locationId);
        }


        return i+j+h;
    }

    @Override
    public int removeLocationCar(Map<String, Object> params) {
        return locationDao.removeLocationCar(params);
    }

    @Override
    public Location findLocationByName(String locationName) {
        return locationDao.findLocationByName(locationName);
    }
}
