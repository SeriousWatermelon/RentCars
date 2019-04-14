package com.work.dao;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.model.Order;
import com.work.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2019/4/12.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderDaoTest {

    @Autowired
    private OrderDao dao;

    @Test
    public void findById() throws Exception {
        Order order = dao.findById("a11");
        log.debug(JSON.toJSONString(order));
    }

    @Test
    public void findOrdersByPage() throws Exception {
        PageHelper.startPage(1,8);
        Page<Order> orders = dao.findOrdersByPage();
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        log.debug(JSON.toJSONString(orderPageInfo));

    }

    @Test
    public void searchOrdersByPage() throws Exception {
        Order order = new Order();

        order.setOrderId("1555076483446350015");
        PageHelper.startPage(1,8);
        Page<Order> orders = dao.searchOrdersByPage(order);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        log.debug(JSON.toJSONString(orderPageInfo));
    }

    @Test
    public void addOrder() throws Exception {
        Order order = new Order();
        order.setOrderId(KeyUtil.getUniqueKey());
        order.setOrderAmount(new BigDecimal(200));
        order.setUserId(5);
        order.setUserName("test");
        order.setUserPhone("13355556666");
        order.setUserAddress("1040 Mt Alexander Rd, Essendon VIC 3040 Australia");
        order.setCarId(2);
        order.setCarMaker("Volkswagen");
        order.setCarType("Small car");
        order.setPickupLocation("Super Market");
        order.setPickupTime(new Date());
        order.setDropoffLocation("Abbe Corrugated");
        order.setDropoffTime(new Date());
        int i = dao.addOrder(order);
        log.debug("i="+i);
    }

    @Test
    public void updateOrder() throws Exception {
        Order order = new Order();
        order.setOrderId("1555076483446350015");
        order.setUserPhone("17788889999");
        order.setDropoffLocation("AD");
        order.setUpdateTime(new Date());
        int i = dao.updateOrder(order);
        log.debug("i="+i);
    }

    @Test
    public void cancleOrder() throws Exception {
        String orderId = "1555076483446350015";
        int i = dao.cancleOrder(orderId);
        log.debug("i="+i);
    }

    @Test
    public void searchUserOrdersByPage(){
        Order order = new Order();
        order.setOrderId("1555076483446350015");
        order.setUserId(2);

        PageHelper.startPage(1,8);
        Page<Order> orders = dao.searchUserOrdersByPage(order);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        System.out.println(JSON.toJSONString(orderPageInfo));
    }

}