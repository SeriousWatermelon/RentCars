package cn.ac.ict.service;

import cn.ac.ict.vo.BraceletInfoVO;
import cn.ac.ict.vo.TtInfectMacRespVO;

import java.util.List;

public interface AppEpidemicService {

    /**
     * 上传mac地址
     *
     * @param braceletInfo
     * @return
     */
    List<TtInfectMacRespVO> macInfoPost(BraceletInfoVO braceletInfo);

}
