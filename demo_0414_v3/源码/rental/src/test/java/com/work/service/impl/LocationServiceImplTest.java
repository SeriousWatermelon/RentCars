package com.work.service.impl;

import com.work.service.LocationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2019/4/12.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationServiceImplTest {

    @Autowired
    private LocationService service;

    @Test
    public void findLocationByPage() throws Exception {
    }

    @Test
    public void searchLocationByPage() throws Exception {
    }

    @Test
    public void findLocationById() throws Exception {
    }

    @Test
    public void updateLocation() throws Exception {
    }

    @Test
    public void addLocation() throws Exception {
    }

    @Test
    public void addLocationCar() throws Exception {
    }

    @Test
    public void findNoLocationCar() throws Exception {
    }

    @Test
    public void updateCarStatus() throws Exception {
    }

    @Test
    public void deleteLocation() throws Exception {
        Integer locationId = 1;
        int i = service.deleteLocation(locationId);
        System.out.println(i);
    }

    @Test
    public void removeLocationCar() throws Exception {
    }

}