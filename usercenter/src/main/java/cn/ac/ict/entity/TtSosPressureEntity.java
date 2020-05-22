package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人员血压表
 *
 * @author cuiweiman
 * @date 2020/5/8 11:20
 */
@Data
@TableName("tt_sos_pressure")
@EqualsAndHashCode(callSuper = false)
public class TtSosPressureEntity extends Model<TtSosPressureEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String mac;

    // 收缩压
    private String spb;

    // 舒张压
    private String dpb;

    private Date createTime;

    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
