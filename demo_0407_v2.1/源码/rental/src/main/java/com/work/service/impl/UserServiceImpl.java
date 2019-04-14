package com.work.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.work.dao.UserDao;
import com.work.model.Role;
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
    public List<User> findUserList() {
        return userDao.findAll();
    }

    @Override
    public Page<User> findByPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return userDao.findByPage();
    }

    @Override
    public Page<User> searchByPage(User user,Integer page, Integer size){
        PageHelper.startPage(page,size);
        return userDao.searchByPage(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
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

        Role role = new ArrayList<>(user.getRoles()).get(0);
        if(role != null){
            saveParams.put("rid",role.getRid());
        }else{
            saveParams.put("rid",2); //普通用户的roleid为2
        }

        userDao.addUserRole(saveParams);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
        int uid = user.getId();

    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
        //更新用户角色中间表
        Map<String, Object> saveParams = new HashMap<>();
        Role role = new ArrayList<>(user.getRoles()).get(0);
        System.out.println(role.toString());
        saveParams.put("rid",role.getRid());
        saveParams.put("uid",user.getId());
        userDao.updateUserRole(saveParams);
    }

    @Override
    public List<Role> findRoleAll() {
        return userDao.findRoleAll();
    }

}
