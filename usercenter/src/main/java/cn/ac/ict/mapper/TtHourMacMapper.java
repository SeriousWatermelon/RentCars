package cn.ac.ict.mapper;

import cn.ac.ict.entity.TtHourMacEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtHourMacMapper extends BaseMapper<TtHourMacEntity> {

    /**
     * 统计 tt_contact_map
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countBetweenContactTime(@Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime);

}
