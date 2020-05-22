package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_sos_sports")
public class TtSosSportsEntity extends Model<TtSosSportsEntity> {

    // 主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 用户id
    private String userId;

    // 手环MAC
    private String mac;

    // 步数
    private Integer steps;

    // 卡路里
    private BigDecimal heat;

    // 距离
    private BigDecimal distance;

    // 运动时间
    private String sportTime;

    // 创建时间
    private Date createTime;

    // 目标步数
    private Integer goalWalk;

    // 详细数据
    private String detailJson;

    // 耗时
    private String timeConsuming;

    // 逻辑删除
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
