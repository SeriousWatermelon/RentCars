package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity extends Model<SysRoleEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 机构ID
    private String bapid;

    // 角色名称
    private String name;

    // 角色代码
    private String code;

    // 角色状态(0正常 1禁用）
    private String status;

    // 角色类型
    private String role_type;

    // 备注
    private String remark;

    private String update_time;

    private String create_time;

    private String update_id;

    private String create_id;

    // 部门id
    private String baid;

    // 租户ID
    private String tenant_id;

}
