package com.work.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.model.Order;
import com.work.service.OrderService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void findOrdersByPage() throws Exception {
        Page<Order> orders = orderService.findOrdersByPage(1,8);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();

        System.out.println(JSON.toJSONString(orderPageInfo.getList()));
    }

}