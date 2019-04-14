package com.work.service;

import com.github.pagehelper.Page;
import com.work.model.Car;

/**
 * Created by 稻草人 on 2019/4/7.
 */
public interface CarService {


    /**
     * 根据汽车id查找汽车信息
     * @param cid
     * @return
     */
    Car findById(Integer cid);

    /**
     * 分页查找汽车列表
     * @return
     */
    Page<Car> findByPage(Integer page,Integer size);

    /**
     * 条件搜索汽车信息
     * @param car
     * @return
     */
    Page<Car> searchByPage(Car car,Integer page,Integer size);

    /**
     * 新增车辆信息
     * @param car
     * @return
     */
    int addCar(Car car);

    /**
     * 更新车辆信息
     * @param car
     * @return
     */
    int updateCar(Car car);




}
