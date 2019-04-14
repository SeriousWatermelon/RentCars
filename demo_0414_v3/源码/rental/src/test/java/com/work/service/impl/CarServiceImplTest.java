package com.work.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.model.Car;
import com.work.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2019/4/10.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CarServiceImplTest {

    @Autowired
    private CarService carService;

    @Test
    public void findLocationCarByPage() throws Exception {
        Integer locationId = 1;
        Integer page = 1;
        Integer size = 8;
        Page<Car> carPage = carService.findLocationCarByPage(locationId,page,size);
        PageInfo<Car> carPageInfo = carPage.toPageInfo();
        System.out.println(carPageInfo.getList().size());

    }

}