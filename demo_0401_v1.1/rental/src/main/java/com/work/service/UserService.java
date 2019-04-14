package com.work.service;

import com.work.model.User;


/**
 * Created by 稻草人 on 2019/3/30.
 */
public interface UserService {

    User findByUsername(String username);

    void registerUser(User user);

}
