package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_wristband_data")
public class TtWristbandDataEntity extends Model<TtWristbandDataEntity> {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 记录插入时间
    private Date createTime;

    // 记录更新时间
    private Date updateTime;

    // 设备类型 蓝牙手环为 Unknow
    private String type;

    // 蓝牙设备mac地址
    private String mac;

    // 蓝牙设备名称
    private String bleName;

    // 信号强度
    private Integer rssi;

    // 蓝牙设备记录的timestamp
    private Date bleTime;

    // 蓝牙设备上传的数据
    private String rawData;

    // 蓝牙网关的mac地址
    private String gatewayMac;

    // 逻辑删除 0-有效数据
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
