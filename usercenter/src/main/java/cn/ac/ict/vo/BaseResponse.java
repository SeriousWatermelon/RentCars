package cn.ac.ict.vo;

import cn.ac.ict.enums.AppCodeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    @ApiModelProperty(value = "返回码：0:正确,-1:错误")
    private int responseCode;

    @ApiModelProperty(value = "返回消息")
    private String responseMsg;

    @ApiModelProperty(value = "返回具体内容")
    private T result;

    public BaseResponse(int responseCode, String responseMsg, T result) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.result = result;
    }

    public BaseResponse(int responseCode, String responseMsg) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public BaseResponse(AppCodeInfo appCodeInfo, T t) {
        this.responseCode = appCodeInfo.getCode();
        this.responseMsg = appCodeInfo.getMsg();
        this.result = t;
    }

}
