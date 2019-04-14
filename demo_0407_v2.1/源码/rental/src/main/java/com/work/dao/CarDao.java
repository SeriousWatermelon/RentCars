package com.work.dao;

import com.github.pagehelper.Page;
import com.work.model.Car;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDao {

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
    Page<Car> findByPage();

    /**
     * 条件搜索汽车信息
     * @param car
     * @return
     */
    Page<Car> searchByPage(Car car);

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
