package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 接触网络 视图对象
 *
 * @author cuiweiman
 * @date 2020/5/11 11:01
 */
@Data
@EqualsAndHashCode
public class TtContactMapVO {

    private String userId;

    private String userName;

    private Integer quarantine;

    private Integer infected;

    Set<TtContactNodeVO> edit;


}

