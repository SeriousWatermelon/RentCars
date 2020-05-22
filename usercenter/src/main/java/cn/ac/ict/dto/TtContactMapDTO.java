package cn.ac.ict.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接触网络 数据传输对象
 *
 * @author cuiweiman
 * @date 2020/5/11 11:01
 */
@Data
@EqualsAndHashCode
public class TtContactMapDTO {

    private String userId;

    private String userName;

    // 是否隔离
    private Integer quarantine;

    // 是否感染
    private Integer infected;

    private String targetId;

    private String targetName;

    // 是否隔离
    private Integer targetQuarantine;

    // 是否感染
    private Integer targetInfected;

}

