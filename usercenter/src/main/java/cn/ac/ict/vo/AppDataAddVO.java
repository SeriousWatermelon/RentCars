package cn.ac.ict.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * APP数据 上传 参数接收 类
 *
 * @param <T>
 */
@Data
public class AppDataAddVO<T> implements Serializable {

    @ApiModelProperty(value = "用户ID", name = "userId", required = true)
    private String userId;

    @ApiModelProperty(value = "数据信息", name = "dataList", required = true)
    private List<T> dataList;

}
