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
@TableName("sys_user")
public class SysUserEntity extends Model<SysUserEntity> {

    // id主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 机构id
    private String bapid;

    // 用户名
    private String userName;

    // 登陆帐户
    private String loginName;

    // 密码
    private String passWord;

    // 新建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 状态(1正常 -1禁用)
    private String status;

    // 电话
    private String phone;

    // 头像路径
    private String photo;

    // 邮箱
    private String email;

    // 创建者
    private String createId;

    // 更新者
    private String updateId;

    // 备注
    private String remark;

    // 部门id
    private String baid;

    // 盐
    private String salt;

    // 所属项目
    private String projectids;

    // 性别
    private String gender;

    // 年龄
    private String age;

    // 身高
    private String stature;

    // 体重
    private String weight;

    // 作废标记 0:未作废 1:已作废
    private String deleteFlag;

    // 作废时间
    private Date deleteTime;

    // 项目ID
    private String projectIds;

    // 目标步数
    private Integer targetSteps;

    // 步长[单位：cm]
    private Integer stepsDistance;

    // 租户ID
    private Long tenantId;

}
