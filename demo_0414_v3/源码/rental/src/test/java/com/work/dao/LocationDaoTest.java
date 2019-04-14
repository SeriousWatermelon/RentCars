package com.work.dao;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.enums.CarStatusEnum;
import com.work.model.Car;
import com.work.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 稻草人 on 2019/4/9.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class LocationDaoTest {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private CarDao carDao;

    @Test
    public void findLocationList() throws Exception{
        List<Location> locations = locationDao.findLocationList();
        for(Location location : locations){
            log.debug(JSON.toJSONString(location.getCars()));
        }
    }

    @Test
    public void findLocationByPage() throws Exception {
        PageHelper.startPage(1,8);
        Page<Location> locations = locationDao.findLocationByPage();
        PageInfo<Location> pageInfo = locations.toPageInfo();
        log.debug(JSON.toJSONString(pageInfo.getList()));
    }

    @Test
    public void searchLocationByPage() throws Exception {
        Location l = new Location();
        l.setLocationName("University of Melbourne");

        PageHelper.startPage(1,8);
        Page<Location> locations = locationDao.searchLocationByPage(l);
        PageInfo<Location> pageInfo = locations.toPageInfo();
        log.debug(JSON.toJSONString(locations));
        log.debug(JSON.toJSONString(pageInfo.getList()));
    }

    @Test
    public void findLocationById() throws Exception {
        Location location = locationDao.findLocationById(1);
        log.debug(JSON.toJSONString(location));
    }

    @Test
    public void updateLocation() throws Exception {
        Location location = locationDao.findLocationById(2);
        location.setLocationName("Melbourne ShowGrounds");
        location.setUpdateTime(new Date());
        int i = locationDao.updateLocation(location);
        log.debug("i="+i);
    }

    @Test
    public void addLocation() throws Exception {
        Location location = new Location();
        location.setLatlng("144.94803696870807,-37.628745811432196");
        location.setLocationName("BP");
        int i = locationDao.addLocation(location);
        log.debug("i="+i);
        log.debug("ID="+location.getLocationId());
    }

    @Test
    public void addLocationCar() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("locationId",8);
        params.put("cid",13);
        int i = locationDao.addLocationCar(params);
        log.debug("i="+i);
    }

    @Test
    public void updateLocationCar() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("locationId",8);
        params.put("cid",2);
        int i = locationDao.updateLocationCar(params);
        log.debug("i="+i);
    }

    @Test
    public void findNoLocationCar() throws Exception {
        List<Car> cars = locationDao.findNoLocationCar();
        for(Car car : cars){
            log.debug(car.toString());
        }
    }

    @Test
    public void updateCarStatus() throws Exception {
        Car car = carDao.findById(12);
        car.setStatus(CarStatusEnum.RECORD.getCode());
        int i = locationDao.updateCarStatus(car);
        log.debug("i="+i);
    }

    @Test
    public void removeLocationCar() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("locationId",8);
        params.put("cid",2);
        locationDao.removeLocationCar(params);

    }

    @Test
    public void findCarsByLocationName() throws Exception{
        Location location = locationDao.findLocationByName("BP");
        log.debug(JSON.toJSONString(location));
        List<Car> cars = location.getCars();
        for(Car car:cars){
            log.debug(JSON.toJSONString(car));
        }
    }
}