package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户信息修改 视图对象
 *
 * @Author: WeiMan Cui
 * @Date: 2020/5/8 22:47
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserVO implements Serializable {

    // id主键
    private String id;

    // 机构id
    private String bapid;

    // 机构名称
    private String bapName;

    // 用户名
    private String userName;

    // 登陆帐户
    private String loginName;

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

    // 性别
    private String gender;

    // 年龄
    private String age;

    // 身高
    private String stature;

    // 体重
    private String weight;

    // 目标步数
    private Integer targetSteps;

    // 手环mac
    private String mac;

    // 步长[单位：cm]
    private Integer stepsDistance;

}
