package cn.ac.ict.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 新增步行——视图参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosSportsVO extends Model<TtSosSportsVO> {

    @ApiModelProperty(value = "步数", name = "walkCounts", required = true)
    private String walkCounts;

    @ApiModelProperty(value = "目标步数", name = "goalWalk", required = true)
    private String goalWalk;

    @ApiModelProperty(value = "热量/卡路里", name = "calorie", required = true)
    private String calorie;

    @ApiModelProperty(value = "耗时", name = "timeConsuming", required = true)
    private String timeConsuming;

    @ApiModelProperty(value = "距离", name = "distance", required = true)
    private String distance;

    @ApiModelProperty(value = "运动时间", name = "startTime", required = true)
    private String startTime;

    @ApiModelProperty(value = "原始数据", name = "detailJson", required = true)
    private String detailJson;

}
