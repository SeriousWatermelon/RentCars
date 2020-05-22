package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_sos_sleep")
public class TtSosSleepEntity extends Model<TtSosSleepEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 数据创建时间
    private Date createTime;

    // 用户ID
    private String userId;

    // 睡眠开始时间
    private Date startTime;

    // 睡眠结束时间
    private Date endTime;

    // 失眠总时间
    private Integer totalTime;

    // 深度睡眠时间 单位s
    private Integer deepTime;

    // 浅睡眠时间 单位s
    private Integer shallowTime;

    // 清醒时间 单位s
    private Integer soberTime;

    // 原始十六进制数据记录
    private String record;

    // 逻辑删除
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
