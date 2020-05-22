package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosPressureEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TtSosPressureService extends IService<TtSosPressureEntity> {

    /**
     * 查询 血压数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosPressureEntity> listPressureInfo(String userId, Date startDate, Date endDate);

    /**
     * 清空 用户血压数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);
}
