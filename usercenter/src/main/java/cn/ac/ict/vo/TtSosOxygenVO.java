package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TtSosOxygenVO implements Serializable {

    @ApiModelProperty(value = "血氧", name = "bloodOxygen")
    private String bloodOxygen;

    @ApiModelProperty(value = "记录日期", name = "datetime")
    private String dateTime;


}
