package cn.ac.ict.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 手环信息
 *
 * @author cuiweiman
 * @date 2020/5/12 8:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BraceletInfoVO implements Serializable {

    private String userId;

    private TtInfectDataVO location;

    private List<TtInfectMacVO> data;

}
