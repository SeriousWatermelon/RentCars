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
@TableName("tt_sos_bind")
public class TtSosBindEntity extends Model<TtSosBindEntity> {

    // id主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 用户id
    private String userId;

    // 手环MAC
    private String mac;

    // 绑定状态,0:未绑定 1:已绑定 2:已解绑
    private String bindStatus;

    // 绑定时间
    private Date bindTime;

    // 解绑时间
    private Date unbindTime;

    // 新建时间
    private Date createTime;


}
