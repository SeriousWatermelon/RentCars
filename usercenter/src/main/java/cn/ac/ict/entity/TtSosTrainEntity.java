package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 训练 数据库对象
 *
 * @author cuiweiman
 * @date 2020/4/3 13:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_sos_train")
public class TtSosTrainEntity extends Model<TtSosTrainEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 创建时间
    private Date createTime;

    // 用户主键
    private String userId;

    // 训练开始时间
    private Date startTime;

    // 训练结束时间
    private Date endTime;

    // 耗时 单位s
    private Integer timeConsuming;

    // 距离
    private String strength;

    // 卡路里
    private String calorie;

    // 步数
    private Integer fasterRate;

    // 详细数据
    private String dataDetail;

    // 位置数据
    private String locationDetail;

    // 逻辑删除[0-有效，其他-被删除]
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
