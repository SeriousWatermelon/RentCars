package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_tenant_info")
public class SysTenantInfoEntity extends Model<SysTenantInfoEntity> {

    /**
     * 自增主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Date createTime;

    private Date updateTime;

    /**
     * 租户名
     */
    private String tenantName;

    /**
     * 租户类型：0-超级租户，1-普通租户
     */
    private Byte tenantType;

    private String createId;

    private String updateId;

    /**
     * 逻辑删除，0-有效
     */
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
