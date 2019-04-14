package com.work.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2019/3/31.
 */
@Getter
public enum ResultEnum {

    SUCCESS(0,"SUCCESS"),
    LOGIN_FAIL(99,"Login failed, user does not exist"),
    LOGOUT_SUCCESS(98,"Logout success"),
    DUPLICATE_USERNAME(97,"Duplicate username"),
    SIGN_UP_FAILED(96,"Sorry, register failed")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
