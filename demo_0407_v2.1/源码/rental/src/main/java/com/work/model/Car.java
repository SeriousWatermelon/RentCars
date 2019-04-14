package com.work.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class Car {

    private Integer cid;

    private String maker;

    private String model;

    private String type;

    private Date productTime;

    private BigDecimal rent;

    private String cicon;

    private Date createTime;

    private Date updateTime;

    private Boolean disable;

}











