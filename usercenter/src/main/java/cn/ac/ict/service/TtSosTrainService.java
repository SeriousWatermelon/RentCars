package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosTrainEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

public interface TtSosTrainService extends IService<TtSosTrainEntity> {

    /**
     * 查询 训练数据
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtSosTrainEntity> listTrainInfo(String userId, Date startDate, Date endDate);

    /**
     * 清空 用户训练数据
     *
     * @return
     */
    Integer deleteByUserId(String userId);
}
