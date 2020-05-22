package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosHeartRateEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TtSosHeartRateService extends IService<TtSosHeartRateEntity> {

    /**
     * 查询 心率数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosHeartRateEntity> listBmpInfo(String userId, Date startDate, Date endDate);

    /**
     * 清空 用户心率数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);

}
