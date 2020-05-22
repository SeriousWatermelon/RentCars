package cn.ac.ict.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 获取个人接触网络 数据对象
 *
 * @author cuiweiman
 * @date 2020/5/11 16:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtContactMapNetDTO {

    // 用户
    private String userId;

    private String userName;

    // 用户的接触人
    private String targetId;

    private String targetName;

    // 接触时间
    private Date contactTime;

    // 接触时间-格式 小时
    private String hhTime;

    // 接触频率
    private Integer contactFrequency;

    // 信号强度
    private String rssi;


}
