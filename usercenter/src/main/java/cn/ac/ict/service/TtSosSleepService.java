package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosSleepEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TtSosSleepService extends IService<TtSosSleepEntity> {

    /**
     * 查询 睡眠数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosSleepEntity> listSleepInfo(String userId, Date startDate, Date endDate);

    /**
     * 清空 用户睡眠数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);
}
