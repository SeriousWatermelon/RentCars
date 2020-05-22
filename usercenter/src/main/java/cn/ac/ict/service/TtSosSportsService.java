package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosSportsEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TtSosSportsService extends IService<TtSosSportsEntity> {

    /**
     * 查询 步行数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosSportsEntity> listWalkInfo(String userId, String startDate, String endDate);

    /**
     * 清空 用户步行数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);
}
