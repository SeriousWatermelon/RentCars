package cn.ac.ict.service;

import cn.ac.ict.vo.GatewayDataVO;

import java.util.List;

/**
 * 蓝牙网关 服务
 *
 * @author cuiweiman
 * @date 2020/5/14 10:31
 */
public interface SosBLEGatewayService {

    /**
     * 网关数据上传
     *
     * @param gatewayDataVOList
     */
    void bleDataUpload(List<GatewayDataVO> gatewayDataVOList);

}
