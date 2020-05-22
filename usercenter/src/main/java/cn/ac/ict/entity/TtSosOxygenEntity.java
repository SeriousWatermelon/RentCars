package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 血氧 实体类
 *
 * @author cuiweiman
 * @date 2020/5/8 11:21
 */
@Data
@TableName("tt_sos_oxygen")
@EqualsAndHashCode(callSuper = false)
public class TtSosOxygenEntity extends Model<TtSosOxygenEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    // 创建时间
    private Date createTime;

    // 记录时间
    private Date dateTime;

    // 血氧
    private String bloodOxygen;

    // 逻辑删除
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;


}
