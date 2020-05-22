package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_hour_mac")
public class TtHourMacEntity extends Model<TtHourMacEntity> {

    // 主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 数据创建时间
    private Date createTime;

    // 用户ID
    private String userId;

    // 接触次数
    private Integer contactNumber;

    // 接触时间
    private Date contactTime;

    // mac地址
    private String mac;

    // 信号强度
    private String rssi;

    // 逻辑删除，0-未删除
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
