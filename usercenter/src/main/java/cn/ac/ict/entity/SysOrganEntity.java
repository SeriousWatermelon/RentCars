package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_organ")
public class SysOrganEntity extends Model<SysOrganEntity> {

    // 该部门的归属机构ID ，只有当是部门的时候才生效
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    // 结点类型：0=根节点 ，1=机构，2=部门
    private String bapid;

    // 编号
    private String type;

    // 节点的名字
    private String code;

    // 节点的父节点
    private String name;

    // 是否删除 0 是 1 否
    private String parent_id;

    // 系统标识，32*10+9 最多支持10级节点。用户具体一批数据的控制
    private String isDel;

    // 在同一级节点中的序号
    private String sysmark;

    // 是否展开 true 是 false 否
    private String sort;

    private String open;

    private String remark;

    private String createId;

    private String createTime;

    private String updateId;

    private String updateTime;

}
