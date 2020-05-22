package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_menu")
public class SysMenuEntity extends Model<SysMenuEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 父菜单id
    private String parentId;

    // 所有父id
    private String parentIds;

    // 菜单名称
    private String name;

    // 菜单链接
    private String url;

    // 菜单图标
    private String icon;

    // 排序
    private String sort;

    // 状态（0显示，1隐藏)
    private String status;

    // 权限标识
    private String permission;

    // 备注
    private String remark;


    private String createTime;


    private String createId;


    private String updateId;


    private String updateTime;


    private String type;

    // 是否展开 true 是 false 否
    private String open;

}
