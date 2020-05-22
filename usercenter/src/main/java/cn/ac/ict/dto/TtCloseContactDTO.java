package cn.ac.ict.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 密切接触者
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtCloseContactDTO implements Serializable {

    /**
     * 接触人 姓名
     */
    private String userName;

    /**
     * 接触时间
     */
    private Date contactTime;

    /**
     * 接触次数
     */
    private Long times;

    /**
     * 信号强度
     */
    private String rssi;

}
