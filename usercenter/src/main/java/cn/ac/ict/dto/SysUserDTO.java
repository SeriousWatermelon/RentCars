package cn.ac.ict.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDTO {

    // id主键
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 机构id
    private String bapid;

    // 机构名称
    private String bapName;

    // 用户名
    private String userName;

    // 登陆帐户
    private String loginName;

    // 新建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 状态(1正常 -1禁用)
    private String status;

    // 电话
    private String phone;

    // 密码
    private String passWord;

    // 盐
    private String salt;

    // 头像路径
    private String photo;

    // 邮箱
    private String email;

    // 是否是 管理员
    private String isAdmin;

    // 部门id
    private String baid;

    // 部门名称
    private String baName;

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

    // 项目ID
    private String projectIds;

    // 目标步数
    private Integer targetSteps;

    // 手环mac
    private String mac;

    // 步长[单位：cm]
    private Integer stepsDistance;


}
