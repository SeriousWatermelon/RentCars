package com.work.model;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {

    private Integer id;

    private String idCard;

    private String username;

    private String password;

    private String phone;

    private String address;

    private Integer credit;

    private Boolean isBlasklist;

    private Date createTime;

    private Date lastLoginTime;

    private Set<Role> roles = new HashSet<>();

}
