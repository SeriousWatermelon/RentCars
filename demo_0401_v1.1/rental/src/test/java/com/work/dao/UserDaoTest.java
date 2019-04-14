package com.work.dao;

import com.work.model.Role;
import com.work.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by 稻草人 on 2019/4/1.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername() throws Exception {
        String username = "admin";
        User user = userDao.findByUsername(username);
        System.out.println(user.toString());
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setId(123456);
        user.setUsername("syy");
        user.setPassword("syy");
        user.setIsBlasklist(false);
        user.setCredit(80);
        user.setAddress("洋海塘");
        user.setIdCard("888888999999666666");
        user.setPhone("15574223387");

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setId(1);
        role.setName("customer");
        roles.add(role);
        user.setRoles(roles);
        int i = userDao.addUser(user); // 影响的行数
        System.out.println(i+";;;"+user.getId());

    }

    @Test
    public void addUserRole() throws Exception {
        Map<String, Object> saveParams = new HashMap<>();
        saveParams.put("uid", 123);
        //这里指定两个角色的ID进行测试
        saveParams.put("rid", 1);

        userDao.addUserRole(saveParams);

    }

}