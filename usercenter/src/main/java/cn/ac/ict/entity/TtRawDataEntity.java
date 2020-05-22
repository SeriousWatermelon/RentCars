package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_raw_data")
public class TtRawDataEntity extends Model<TtRawDataEntity> {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Date createTime;

    private Date updateTime;

    // 厂商ID
    private String factoryId;

    // 蓝牙设备mac地址
    private String mac;

    // rawData解析的timestamp
    private Date time;

    // 步数
    private String stepCount;

    // 行走里程/行走距离
    private String mileage;

    // 卡路里
    private String calorie;

    // 剩余电量
    private String power;

    // 求救信号[1-求救]
    private String sos;

    // 体温
    private String temperature;

    // 睡眠时间
    private String sleepTime;

    // 逻辑删除[0-有效]
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
