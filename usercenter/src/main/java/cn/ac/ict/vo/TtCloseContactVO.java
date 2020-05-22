package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 密切接触者
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtCloseContactVO implements Serializable {

    /**
     * 接触人 姓名
     */
    private String userName;

    /**
     * 接触时间
     */
    private Long contactTime;

    /**
     * 接触次数
     */
    private Long times;

    /**
     * 接触距离
     */
    private String distance;

}
