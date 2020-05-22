package cn.ac.ict.exception;

import cn.ac.ict.enums.AppCodeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 全局异常
 *
 * @author cuiweiman
 * @date 2020/5/7 17:45
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GlobalException extends RuntimeException {

    private Integer code;

    private String msg;

    public GlobalException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public GlobalException(AppCodeInfo appCodeInfo) {
        this.code = appCodeInfo.getCode();
        this.msg = appCodeInfo.getMsg();
    }

}

