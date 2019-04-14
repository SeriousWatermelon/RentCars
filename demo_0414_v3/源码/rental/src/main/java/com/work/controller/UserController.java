package com.work.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.model.Role;
import com.work.model.User;
import com.work.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(value = "/staff/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<User> users = userService.findByPage(page,size);
        PageInfo<User> pageInfo = users.toPageInfo();

        map.put("pageInfo",pageInfo);
        map.put("currentPage",page);
        map.put("size",size);

        return new ModelAndView("/staff/userList",map);
    }

    @GetMapping(value = "/resetCredit")
    public ModelAndView resetCredit(
            @RequestParam(value="id") Integer id,
            Map<String,Object> map){
        User user = userService.findUserById(id);
        if(user == null){
            map.put("msg","No Such Account");
            map.put("url","/staff/user/list");
            return new ModelAndView("/common/error",map);
        }
        log.info("[ResetCredit]username={},credit={}",user.getUsername(),user.getCredit());
        user.setCredit(80);
        userService.updateUser(user);
        map.put("msg","Reset Credit Success");
        map.put("url","/staff/user/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping(value = "/blacklist")
    public ModelAndView blacklist(
            @RequestParam(value="id") Integer id,
            Map<String,Object> map){
        User user = userService.findUserById(id);
        if(user == null){
            map.put("msg","No Such Account");
            map.put("url","/staff/user/list");
            return new ModelAndView("/common/error",map);
        }
        if(user.getIsBlacklist()){//true：黑名单状态；则此操作是移出黑名单
            user.setIsBlacklist(false);
            log.info("[RemoveFromBlack]username={}",user.getUsername());
        }else{//false：不在黑名单中；故此操作是拉入黑名单
            user.setIsBlacklist(true);
            log.info("[PullIntoBlack]username={}",user.getUsername());
        }
        userService.updateUser(user);

        map.put("msg","Reset Credit Success");
        map.put("url","/staff/user/list");
        return new ModelAndView("/common/success",map);
    }


    @GetMapping(value = "/edit")
    public ModelAndView edit(
            @RequestParam(value = "id",required = false) Integer id,
            Map<String,Object> map){
        if(id != null ){
            User user = userService.findUserById(id);
            map.put("userInfo",user);
            Role role = new ArrayList<>(user.getRoles()).get(0);
            map.put("uRole",role);
        }
        List<Role> rolesInfo = userService.findRoleAll();
        map.put("rolesInfo",rolesInfo);
        return new ModelAndView("/staff/userEdit");
    }

    @PostMapping(value = "/save")
    public ModelAndView save(User user,Integer roleId,
                             Map<String,Object> map){
        Role role = new Role();
        role.setRid(roleId);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        if(user.getId() == null){//新增
            userService.registerUser(user);
            log.info("[AddNewer]UserInfo={}",user.toString());
        }else{//修改
            userService.updateUser(user);
            log.info("[UpdateUserInfo]UserInfo={}",user.toString());
        }
        map.put("msg", "Operation Success");
        map.put("url","/staff/user/list");
        return new ModelAndView("/common/success",map);
    }

    @PostMapping(value = "/search")
    public ModelAndView search(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            User user, Map<String,Object> map){

        Page<User> users = userService.searchByPage(user,page,size);
        PageInfo<User> pageInfo = users.toPageInfo();

        map.put("pageInfo",pageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/userList",map);
    }


}






