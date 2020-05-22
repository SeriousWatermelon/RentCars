package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosOxygenEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TtSosOxygenService extends IService<TtSosOxygenEntity> {

    /**
     * 查询 血氧数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosOxygenEntity> listOxygenInfo(String userId, Date startDate, Date endDate);

    /**
     * 清空 用户血氧数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);
}
