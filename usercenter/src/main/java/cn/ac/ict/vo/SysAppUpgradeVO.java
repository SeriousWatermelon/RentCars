package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysAppUpgradeVO implements Serializable {

    @ApiModelProperty(value = "英文名", name = "name")
    private String name;

    @ApiModelProperty(value = "中文名", name = "chiName")
    private String chiName;

    @ApiModelProperty(value = "版本号", name = "version")
    private String version;

    @ApiModelProperty(value = "更新日志", name = "noteLog")
    private String noteLog;

    @ApiModelProperty(value = "更新备注", name = "remark")
    private String remark;

}
