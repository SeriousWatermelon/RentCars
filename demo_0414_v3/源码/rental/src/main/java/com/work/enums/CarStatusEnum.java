package com.work.enums;

import lombok.Getter;

/**
 * Created by 稻草人 on 2019/3/31.
 */
@Getter
public enum CarStatusEnum {
    NEW_CAR(0,"New Car"),
    RECORD(1,"Recorded"),
    BEING_USING(2,"Using...")
    ;

    private Integer code;

    private String message;

    CarStatusEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
}
