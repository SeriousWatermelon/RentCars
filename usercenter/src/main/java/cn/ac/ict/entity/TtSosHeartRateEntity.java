package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 人员心率表
 *
 * @author cuiweiman
 * @date 2020/5/8 11:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_sos_heartrate")
public class TtSosHeartRateEntity extends Model<TtSosHeartRateEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    // 手环MAC
    private String mac;

    private String bmp;

    // 创建时间
    private Date createTime;

    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
