package com.work.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.work.dao.CarDao;
import com.work.model.Car;
import com.work.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 稻草人 on 2019/4/7.
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarDao carDao;

    @Override
    public Car findById(Integer cid) {
        return carDao.findById(cid);
    }

    @Override
    public Page<Car> findByPage(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return carDao.findByPage();
    }

    @Override
    public Page<Car> searchByPage(Car car,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return carDao.searchByPage(car);
    }

    @Override
    public int addCar(Car car) {
        return carDao.addCar(car);
    }

    @Override
    public int updateCar(Car car) {
        return carDao.updateCar(car);
    }
}
