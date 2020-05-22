package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtContactMapReqVO implements Serializable {

    @ApiModelProperty(value = "部门ID", name = "baId")
    private String baId;

    @ApiModelProperty(value = "用户姓名", name = "userName")
    private String userName;

    @ApiModelProperty(value = "是否隔離。0未隔离，1已隔离，2全部", name = "quarantine")
    private Integer quarantine;

    @ApiModelProperty(value = "是否感染。0未感染，1感染，2全部", name = "infected")
    private Integer infected;

}
