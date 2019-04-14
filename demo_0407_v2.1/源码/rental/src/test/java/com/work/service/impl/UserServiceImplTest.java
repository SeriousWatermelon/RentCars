package com.work.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.model.Role;
import com.work.model.User;
import com.work.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by 稻草人 on 2019/4/1.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByUsername() throws Exception {
    }

    @Test
    public void registerUser() throws Exception {
        User user = new User();
        user.setId(123456);
        user.setUsername("syy");
        user.setPassword("syy");
        user.setIsBlacklist(false);
        user.setCredit(80);
        user.setAddress("洋海塘");
        user.setIdCard("888888999999666666");
        user.setPhone("15574223387");

        userService.registerUser(user);
        System.out.println(user.getId());
    }

    @Test
    public void findByPages(){
        Page<User> users = userService.findByPage(2,2);
        PageInfo<User> pageInfo = users.toPageInfo();
        System.out.println(users.toString());
        System.out.println(pageInfo.toString());
    }

}