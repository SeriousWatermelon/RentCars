package com.work.dao;

import com.github.pagehelper.Page;
import com.work.model.Role;
import com.work.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by 稻草人 on 2019/3/30.
 */
@Repository
public interface UserDao {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 分页查询所有用户
     * @return
     */
    Page<User> findByPage();

    /**
     * 分页搜索用户
     * @param user
     * @return
     */
    Page<User> searchByPage(User user);

    /**
     * 根据用户名称查找用户
     * @param username
     * @return
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    User findUserById(Integer id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 插入用户_角色表
     * @param params
     * @return
     */
    int addUserRole(Map<String,Object> params);

    /**
     * 更新用户_角色表
     * @param params
     * @return
     */
    int updateUserRole(Map<String,Object> params);

    /**
     * 查找所有角色
     * @return
     */
    List<Role> findRoleAll();

}
