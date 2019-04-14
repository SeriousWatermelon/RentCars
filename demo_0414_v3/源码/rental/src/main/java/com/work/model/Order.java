package com.work.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by 稻草人 on 2019/4/8.
 */

@Data
public class Order {

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户住址
     */
    private String userAddress;

    /**
     * 车辆编号
     */
    private Integer carId;

    /**
     * 车辆制造商
     */
    private String carMaker;

    /**
     * 车型：大型小型中型等
     */
    private String carType;

    /**
     * 取车地址
     */
    private String pickupLocation;


    /**
     * 取车时间
     */
    private Date pickupTime;

    /**
     * 还车地址
     */
    private String dropoffLocation;


    /**
     * 还车时间
     */
    private Date dropoffTime;

    /**
     * 订单状态。0：取车中；1使用中；2完结。
     */
    private Integer orderStatus;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单修改时间
     */
    private Date updateTime;

}
