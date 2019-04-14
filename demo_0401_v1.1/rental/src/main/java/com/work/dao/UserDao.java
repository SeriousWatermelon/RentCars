package com.work.dao;

import com.work.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by 稻草人 on 2019/3/30.
 */
public interface UserDao {

    User findByUsername(@Param("username") String username);

    int addUser(User user);

    int addUserRole(Map<String,Object> params);


}
