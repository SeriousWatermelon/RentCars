package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
public class SysUserRoleEntity extends Model<SysUserRoleEntity> {

    private String userId;

    private String roleId;

}
