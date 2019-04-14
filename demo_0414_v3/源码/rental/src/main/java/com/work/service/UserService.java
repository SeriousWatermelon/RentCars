package com.work.service;

import com.github.pagehelper.Page;
import com.work.model.Role;
import com.work.model.User;

import java.util.List;


/**
 * Created by 稻草人 on 2019/3/30.
 */
public interface UserService {

    List<User> findUserList();

    /**
     * 分页查询用户列表
     * @param page 当前页码
     * @param size 页码记录数
     * @return
     */
    Page<User> findByPage(Integer page,Integer size);

    /**
     * 分页查询
     * @param user 查询条件
     * @param page 当前页码
     * @param size 每页记录数
     * @return
     */
    Page<User> searchByPage(User user,Integer page, Integer size);

    /**
     * 根据姓名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findUserById(Integer id);

    /**
     * 用户注册
     * @param user
     */
    void registerUser(User user);

    /**
     * 新增用户
     * @param user
     */
    void addUser(User user);

    /**
     * 用户信息修改
     * @param user
     */
    void updateUser(User user);

    List<Role> findRoleAll();

    int updateUserInfo(User user);
}
