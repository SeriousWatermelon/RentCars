package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 新增睡眠——视图参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosSleepVO implements Serializable {

    @ApiModelProperty(value = "睡眠开始时间", name = "startTime")
    private String startTime;

    @ApiModelProperty(value = "睡眠结束时间", name = "endTime")
    private String endTime;

    @ApiModelProperty(value = "失眠总时间", name = "totalTime")
    private Integer totalTime;

    @ApiModelProperty(value = "深度睡眠时间 单位s", name = "deepTime")
    private Integer deepTime;

    @ApiModelProperty(value = "浅睡眠时间 单位s", name = "shallowTime")
    private Integer shallowTime;

    @ApiModelProperty(value = "清醒时间 单位s", name = "soberTime")
    private Integer soberTime;

    @ApiModelProperty(value = "原始十六进制数据记录", name = "record")
    private String record;

}
