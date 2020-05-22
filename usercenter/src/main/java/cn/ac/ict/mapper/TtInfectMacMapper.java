package cn.ac.ict.mapper;

import cn.ac.ict.dto.TtCloseContactDTO;
import cn.ac.ict.entity.TtInfectMacEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtInfectMacMapper extends BaseMapper<TtInfectMacEntity> {

    /**
     * 密切接触者查询
     *
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    List<TtCloseContactDTO> listCloseContacts(@Param("userName") String userName,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    /**
     * 查询 个人 接触人数
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countContactNumber(@Param("userId") String userId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    /**
     * 统计 tt_hour_mac
     *
     * @return
     */
    List<Map<String, Object>> countByMacGroupHours(@Param("contactTime") Date contactTime);

}
