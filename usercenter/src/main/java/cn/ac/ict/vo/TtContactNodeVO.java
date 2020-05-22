package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接触网络 节点 视图对象
 *
 * @author cuiweiman
 * @date 2020/5/11 11:02
 */
@Data
@EqualsAndHashCode
public class TtContactNodeVO {

    String sourceId;

    String targetId;

    private String sourceName;

    private String targetName;

    private Integer targetQuarantine;

    private Integer targetInfected;


}
