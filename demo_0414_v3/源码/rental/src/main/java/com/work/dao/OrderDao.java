package com.work.dao;

import com.github.pagehelper.Page;
import com.work.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by 稻草人 on 2019/4/8.
 */

@Repository
public interface OrderDao {

    /**
     * 根据订单编号查找订单
     * @param orderId
     * @return
     */
    Order findById(String orderId);

    /**
     * 分页查找所有订单
     * @return
     */
    Page<Order> findOrdersByPage();

    /**
     * 条件查询订单
     * @param order
     * @return
     */
    Page<Order> searchOrdersByPage(Order order);

    /**
     * 添加订单
     * @param order
     * @return
     */
    int addOrder(Order order);

    /**
     * 修改订单
     * @param order
     * @return
     */
    int updateOrder(Order order);

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    int cancleOrder(String orderId);

    /**
     * 查找用户订单
     * @param id
     * @return
     */
    Page<Order> findOrdersByUserId(Integer id);


    Page<Order> searchUserOrdersByPage(Order order);

}
