package cn.ac.ict.enums;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum AppCodeInfo implements Serializable {

    // 状态码
    SUCCESS(0, "请求成功"),
    FAILED(-1, "请求失败"),
    SYS_ERROR(500, "系统错误"),
    SYS_WEB_SOCKET(8888, "通知前端更新"),
    PARAM_NOT_EMPTY(504, "请求参数不能为空"),

    PARAM_ERROR(10000, "参数错误"),

    // App 数据上传后台 AppDataController 10001-10010
    START_END_TIME_ERROR(10001, "开始时间不能大于或等于结束时间"),
    USER_ID_EMPTY(10002, "用户ID不能为空"),
    DATA_LIST_EMPTY(10003, "数据不能为空"),
    DATE_FORMAT_ERROR(10004, "时间格式错误"),


    // APP 系统维护 AppMaintainController 100011-10020
    FILE_EMPTY_ERROR(10011, "文件不能为空"),

    // App 用户信息中心 AppUserCenterController 100021-10030
    ACCOUNT_NOT_EXISTS(100021, "用户信息不存在"),
    ACCOUNT_PASS_ERROR(100022, "账号或密码错误"),
    ACCOUNT_FORBIDDEN(100023, "该账号已被禁用"),
    ACCOUNT_REPEATED(100024, "该账号已被注册"),
    TENANT_NOT_EXISTS(100025, "该用户不属于任何租户"),

    // App 用户绑定手环 AppBindWatchController 100031-10040
    USER_BIND(100031, "该用户已绑定手环，请先解绑"),
    MAC_BIND(100032, "该MAC地址已被绑定，请先解绑"),
    USER_NOT_BIND(100033, "该用户未绑定手环,不能解绑"),
    USER_MAC_NOT_BIND(100034, "解绑MAC地址与绑定MAC地址不一致"),

    // 防疫信息接口 AppEpidemicController 100041-100060


    ;

    private String msg;
    private Integer code;

    AppCodeInfo(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

}
