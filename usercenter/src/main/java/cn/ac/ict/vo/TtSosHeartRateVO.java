package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosHeartRateVO implements Serializable {

    @ApiModelProperty(value = "心率", name = "bpm")
    private String bmp;

    @ApiModelProperty(value = "创建时间", name = "createTime")
    private String createTime;


}
