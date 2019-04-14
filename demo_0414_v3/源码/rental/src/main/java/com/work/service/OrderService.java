package com.work.service;

import com.github.pagehelper.Page;
import com.work.model.Order;
import org.springframework.stereotype.Service;

/**
 * Created by 稻草人 on 2019/4/8.
 */

@Service
public interface OrderService {

    /**
     * 根据订单id查询
     * @param orderId
     * @return
     */
    Order findById(String orderId);

    /**
     * 分页查询订单列表
     * @return
     */
    Page<Order> findOrdersByPage(Integer page,Integer size);

    /**
     * 根据查询条件，分页查询订单列表
     * @param order
     * @return
     */
    Page<Order> searchOrdersByPage(Order order,Integer page,Integer size);

    /**
     * 新增订单: 注意修改车辆状态
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
     * 取消订单：注意恢复车辆状态
     * @param orderId
     * @return
     */
    int cancleOrder(String orderId);


    /**
     * 根据用户id查询用户订单
     * @param id
     * @return
     */
    Page<Order> findOrdersByUserId(Integer id,Integer page,Integer size);

    /**
     * 用户查询自己的订单
     * @param order
     * @param
     * @param page
     * @param size
     * @return
     */
    Page<Order> searchUserOrdersByPage(Order order,Integer page,Integer size);

}
