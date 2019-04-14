package com.work.service.impl;

import com.work.dao.UserDao;
import com.work.model.User;
import com.work.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 稻草人 on 2019/3/30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public void registerUser(User user) {
        //1. 存储用户信息
        userDao.addUser(user);
        //2. 存储用户信息和角色中间表
        int uid = user.getId();
        Map<String, Object> saveParams = new HashMap<>();
        saveParams.put("uid",uid);
        saveParams.put("rid",2); //普通用户的roleid为2
        userDao.addUserRole(saveParams);
    }

}
