package com.work.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.work.dao.OrderDao;
import com.work.model.Order;
import com.work.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 稻草人 on 2019/4/8.
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;


    @Override
    public Order findById(String orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public Page<Order> findOrdersByPage(Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return orderDao.findOrdersByPage();
    }

    @Override
    public Page<Order> searchOrdersByPage(Order order,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return orderDao.searchOrdersByPage(order);
    }

    @Transactional
    @Override
    public int addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Transactional
    @Override
    public int updateOrder(Order order) {
        return orderDao.updateOrder(order);
    }

    @Transactional
    @Override
    public int cancleOrder(String orderId) {
        return orderDao.cancleOrder(orderId);
    }

    @Override
    public Page<Order> findOrdersByUserId(Integer id,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        return orderDao.findOrdersByUserId(id);
    }

    @Override
    public Page<Order> searchUserOrdersByPage(Order order,Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return orderDao.searchUserOrdersByPage(order);
    }
}
