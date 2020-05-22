package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TtInfectMacRespVO implements Serializable {

    private String id;

    private String userId;

    private Long createTime;

    private String mac;

    private String rssi;

    /**
     * 记录 上传mac地址 接口 是否保存成功。0：成功；-1：失败。
     */
    private Integer status;

}
