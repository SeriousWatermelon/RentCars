package cn.ac.ict.service;

import cn.ac.ict.entity.TtHourMacEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtHourMacService extends IService<TtHourMacEntity> {

    /**
     * 根据接触时间删除
     *
     * @param contactTime
     * @return
     */
    Integer deleteByContactTime(Date contactTime);


    /**
     * 统计 tt_contact_map
     *
     * @param contactTime
     * @return
     */
    List<Map<String, Object>> countByContactTime(Date contactTime);
}
