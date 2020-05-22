package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtSosBindVO implements Serializable {

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "手环MAC")
    private String mac;

}
