package cn.ac.ict.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtContactMapRespDTO implements Serializable {

    @ApiModelProperty(value = "用户Id", name = "userId")
    private String userId;

    @ApiModelProperty(value = "部门名称", name = "baName")
    private String baName;

    @ApiModelProperty(value = "用户姓名", name = "userName")
    private String userName;

    @ApiModelProperty(value = "是否隔離。0未隔离，1已隔离", name = "quarantine")
    private Integer quarantine;

    @ApiModelProperty(value = "是否感染。0未感染，1感染", name = "infected")
    private Integer infected;

}
