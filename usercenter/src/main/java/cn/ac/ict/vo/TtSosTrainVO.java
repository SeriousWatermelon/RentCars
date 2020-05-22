package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 训练 数据库对象
 *
 * @author cuiweiman
 * @date 2020/4/3 13:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosTrainVO implements Serializable {

    @ApiModelProperty(value = "训练开始时间", name = "startTime")
    private String startTime;

    @ApiModelProperty(value = "训练结束时间", name = "endTime")
    private String endTime;

    @ApiModelProperty(value = "耗时", name = "timeConsuming")
    private String timeConsuming;

    @ApiModelProperty(value = "距离", name = "strength")
    private String strength;

    @ApiModelProperty(value = "卡路里", name = "calorie")
    private String calorie;

    @ApiModelProperty(value = "步数", name = "fasterRate")
    private String fasterRate;

    @ApiModelProperty(value = "详细数据", name = "dataDetail")
    private String dataDetail;

    @ApiModelProperty(value = "位置数据", name = "locationDetail")
    private String locationDetail;

}
