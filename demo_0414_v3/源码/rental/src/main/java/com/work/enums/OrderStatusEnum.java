package com.work.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2019/3/31.
 */
@Getter
public enum OrderStatusEnum {

    SUCCESS(0,"SUCCESS"),
    LOGIN_FAIL(99,"Login failed, user does not exist"),
    LOGOUT_SUCCESS(98,"Logout success"),
    DUPLICATE_USERNAME(97,"Duplicate username"),
    SIGN_UP_FAILED(96,"Sorry, register failed"),
    ORDER_PICKING_UP(0,"Pick up the car"),
    ORDER_USING(1,"Car in using"),
    ORDER_FINISHED(2,"Order Finished"),
    ORDER_CANCEL(3,"Cancel Order")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
