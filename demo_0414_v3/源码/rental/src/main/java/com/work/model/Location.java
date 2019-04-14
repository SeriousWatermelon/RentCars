package com.work.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 稻草人 on 2019/4/8.
 */
@Data
public class Location {

    /**
     * 网点编号
     */
    private Integer locationId;

    /**
     * 网点经纬度
     */
    private String latlng;

    /**
     * 网点名称
     */
    private String locationName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 网点车辆集合
     */
    private List<Car> cars = new ArrayList<>();

}
