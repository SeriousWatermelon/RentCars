package com.work.dao;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.work.model.Role;
import com.work.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.*;



/**
 * Created by 稻草人 on 2019/4/1.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findByUsername() throws Exception {
        String username = "admin";
        User user = userDao.findByUsername(username);
        Role role = new ArrayList<Role>(user.getRoles()).get(0);
        System.out.println(role.toString());
        System.out.println(user.toString());
    }

    @Test
    public void addUser() throws Exception {
        User user = new User();
        user.setId(123456);
        user.setUsername("syy");
        user.setPassword("syy");
        user.setIsBlacklist(false);
        user.setCredit(80);
        user.setAddress("洋海塘");
        user.setIdCard("888888999999666666");
        user.setPhone("15574223387");

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setRid(1);
        role.setName("customer");
        roles.add(role);
        user.setRoles(roles);
        int i = userDao.addUser(user); // 影响的行数
        System.out.println(i+";;;"+user.getId());

    }

    @Test
    public void updateUser(){
        User user = new User();
        user.setId(35);
        user.setUsername("admin2");
        user.setPassword("a123456789");
        user.setIsBlacklist(false);
        user.setCredit(80);
        user.setAddress("上海市静安区");
        user.setIdCard("888888999999666666");
        user.setPhone("17766662222");
        user.setLastLoginTime(new Date());
        int i = userDao.updateUser(user);
        System.out.println("i="+i+"; id="+user.getId());
    }

    @Test
    public void addUserRole() throws Exception {
        Map<String, Object> saveParams = new HashMap<>();
        saveParams.put("uid", 123);
        //这里指定两个角色的ID进行测试
        saveParams.put("rid", 1);

        userDao.addUserRole(saveParams);

    }

    @Test
    public void findAll(){
        List<User> userList = userDao.findAll();
        for(User user:userList){
            System.out.println(user.toString());
        }
    }

    @Test
    public void findByPage(){
        PageHelper.startPage(1, 8);

        Page<User> users = userDao.findByPage();
        // 需要把Page包装成PageInfo对象才能序列化。该插件也默认实现了一个PageInfo
        PageInfo<User> pageInfo = users.toPageInfo();
        Assert.assertNotNull(users);
        log.debug(pageInfo.toString());
        log.debug(JSON.toJSONString(pageInfo));
        log.debug(JSON.toJSONString(users));
    }

    @Test
    public void findUserById(){
        Integer id = 1;
        User user = userDao.findUserById(id);
        System.out.println(user.toString());
    }

    @Test
    public void findRoleAll(){
        List<Role> roles = userDao.findRoleAll();
        for(Role role : roles){
            System.out.println(role.toString());
        }
    }

    @Test
    public void search(){
        User user = new User();
        Page<User> userPage = userDao.searchByPage(user);
        PageInfo<User> pageInfo = userPage.toPageInfo();
        System.out.println(userPage.toString());
        System.out.println(pageInfo.toString());
    }

}