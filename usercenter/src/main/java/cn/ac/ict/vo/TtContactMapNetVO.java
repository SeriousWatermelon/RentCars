package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 获取个人接触网络 视图对象
 *
 * @author cuiweiman
 * @date 2020/5/11 16:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TtContactMapNetVO implements Serializable {

    // 接触人
    private String userId;

    private String userName;

    // 接触时间
    private Long contactTime;

    // 接触频率
    private Integer contactFrequency;

    // 接触距离
    private String distance;

    private List<TtContactMapNetVO> childData;

}
