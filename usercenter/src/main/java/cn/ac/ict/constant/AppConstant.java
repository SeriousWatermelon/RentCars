package cn.ac.ict.constant;

public class AppConstant {

    // 租户类型 0-超级租户 1-普通租户
    public static final Byte TENANT_TYPE_SUPER = 0;
    public static final Byte TENANT_TYPE_ORDINARY = 1;

    // 超级租户 下 超级管理员ID
    public static final String SUPER_USER_ID = "026a564bbfd84861ac4b65393644beef";

    // menu类型 0-目录，1-菜单，2-按钮
    public static final String MENU_TYPE_CATALOG = "0";
    public static final String MENU_TYPE_MENU = "1";
    public static final String MENU_TYPE_BUTTON = "2";

    // 账号 可用状态 0正常账号，-1封号禁用
    public static final String ACCOUNT_STATUS_FORBIDDEN = "-1";
    public static final String ACCOUNT_STATUS_ORDINARY = "0";

    // 角色状态 0正常，1禁用
    public static final String ROLE_STATUS_FORBIDDEN = "1";
    public static final String ROLE_STATUS_ORDINARY = "0";

    // 注册账号，默认分配到的 机构 和 部门
    public static final String ACCOUNT_DEFAULT_BAP_ID = "7180f5a0c3624f4bb6fb758ab2c3bda6";
    public static final String ACCOUNT_DEFAULT_BA_ID = "0eec919538bd4cd7bf3b1aebd54808f5";

    // 注册账户，默认分配的 角色ID
    public static final String ACCOUNT_DEFAULT_ROLE_ID = "f6741eb305a540508265f3bf7f79baaa";


    // 文件上传到服务器的 路径 /sosms/upload/
    public static final String APP_UPGRADE_PATH = "/static/upload/";
    public static final String APP_UPGRADE_DOWNLOAD_PATH = "/sosms/upload/";

    // 客户端软件更新标识
    public static final Integer APP_UPGRADE_TYPE_ANDROID = 0;
    public static final Integer APP_UPGRADE_TYPE_IOS = 1;

    // 用户默认头像
    public static final String DEFAULT_APP_HEADER = "/sosms/images/default_header.png";

    // -1-全部，1-机构，2-部门
    public static final String SYS_ORGAN_ALL = "-1";
    public static final String SYS_ORGAN_ORGAN = "1";
    public static final String SYS_ORGAN_DEPT = "2";

    // 手环 绑定标识 0-未绑定，1-绑定，2-解绑
    public static final String USER_MAC_NOT_BIND = "0";
    public static final String USER_MAC_BIND = "1";
    public static final String USER_MAC_UN_BIND = "2";

    // 0-对应感染字段 1-对应隔离字段
    public static final Integer TT_CONTACT_MAP_INFECTED = 0;
    public static final Integer TT_CONTACT_MAP_QUARANTINE = 1;

    // 蓝牙网关 的 类型
    public static final String BLE_GATEWAY_TYPE = "Gateway";

}
