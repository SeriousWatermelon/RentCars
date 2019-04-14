package com.work.dao;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2019/4/7.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CarDaoTest {

    @Autowired
    private CarDao carDao;

    @Test
    public void findById() throws Exception {
        Integer cid = 1;
        Car car = carDao.findById(cid);
        log.debug(JSON.toJSONString(car));
    }

    @Test
    public void findByPage() throws Exception {
        PageHelper.startPage(1,8);
        Page<Car> carPage = carDao.findByPage();
        PageInfo<Car> pageInfo = carPage.toPageInfo();
        log.debug(JSON.toJSONString(carPage));
        log.debug(JSON.toJSONString(pageInfo));
    }

    @Test
    public void searchByPage() throws Exception {
        PageHelper.startPage(1,8);
        Car car = new Car();
        car.setMaker("ford");
        car.setModel("fox");
        Page<Car> carPage = carDao.searchByPage(car);
        PageInfo<Car> pageInfo = carPage.toPageInfo();
        log.debug(JSON.toJSONString(carPage));
        log.debug(JSON.toJSONString(pageInfo));
    }

    @Test
    public void addCar() throws Exception {
        Car car = new Car();
        car.setMaker("Toyota");
        car.setModel("Hanlanda");
        car.setType("SUV");
        car.setProductTime(new SimpleDateFormat("yyyy-MM-dd").parse("2016-08-06"));
        car.setRent(new BigDecimal(100));
        car.setDisable(true);
        int i = carDao.addCar(car);
        int cid = car.getCid();
        log.debug("i="+i+"; "+"cid="+cid);
    }

    @Test
    public void updateCar() throws Exception {
        Car car = new Car();
        car.setCid(8);
        car.setMaker("Toyota");
        car.setModel("Hanlanda");
        car.setType("SUV");
        car.setProductTime(new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-06"));
        car.setRent(new BigDecimal(110));
        car.setDisable(true);
        int i = carDao.updateCar(car);
        int cid = car.getCid();
        log.debug("i="+i+"; "+"cid="+cid);
    }

    @Test
    public void findLocationCar(){
        Integer locationId = 8;
        Page<Car> cars = carDao.findLocationCar(locationId);
        PageInfo<Car> carPageInfo = cars.toPageInfo();
        log.debug(JSON.toJSONString(carPageInfo));
    }


    @Test
    public void timeTest(){

        String stateTime = "2016-10-24 05:00:00";
        String endTime = "2016-10-24 15:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date state = sdf.parse(stateTime);
            Date end  = sdf.parse(endTime);

            long stateTimeLong = state.getTime();
            long endTimeLong = end.getTime();
            // 结束时间-开始时间 = 天数
            long day = (endTimeLong-stateTimeLong)/(60*60*1000);
            System.out.println(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}