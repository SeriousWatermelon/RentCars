package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosPressureVO implements Serializable {

    @ApiModelProperty(value = "收缩压", name = "sbp")
    private String sbp;

    @ApiModelProperty(value = "舒张压", name = "dbp")
    private String dbp;

    @ApiModelProperty(value = "创建时间", name = "dateTime")
    private String dateTime;

}
