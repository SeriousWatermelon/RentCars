package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppDataQueryVO implements Serializable {

    @ApiModelProperty(value = "用户ID", name = "userId", required = true)
    private String userId;

    @ApiModelProperty(value = "开始日期", name = "startDate", required = true)
    private String startDate;

    @ApiModelProperty(value = "截止日期", name = "endDate", required = true)
    private String endDate;

}
