package cn.ac.ict.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_app_upgrade")
public class SysAppUpgradeEntity extends Model<SysAppUpgradeEntity> {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建和修改时间—
     */
    private Date addDate;

    private Date updateDate;


    /**
     * 英文名
     */
    private String name;


    /**
     * 中文名
     */
    private String chiName;


    /**
     * 文件名
     */
    private String fileName;


    /**
     * 客户端类型：0-Android，1—IOS
     */
    private Integer type;


    /**
     * 版本号
     */
    private String version;

    /**
     * 文件 大小 单位 KB
     */
    private String size;

    /**
     * 更新日志
     */
    private String noteLog;

    /**
     * 更新备注
     */
    private String remark;

    /**
     * 下载链接
     */
    private String downloadUrl;


    /**
     * 逻辑删除：0-未删除；时间戳——已删除。
     */
    @TableLogic
    @TableField(select = false)
    private Long isDeleted;

}
