package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tt_infect_mac")
public class TtInfectMacEntity extends Model<TtInfectMacEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 用户ID
    private String userId;

    private Date createTime;

    // mac地址
    private String mac;

    private Integer status;

    // 信号强度
    private String rssi;

}
