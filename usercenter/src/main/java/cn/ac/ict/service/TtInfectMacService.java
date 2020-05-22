package cn.ac.ict.service;

import cn.ac.ict.dto.TtCloseContactDTO;
import cn.ac.ict.entity.TtInfectMacEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtInfectMacService extends IService<TtInfectMacEntity> {

    /**
     * 密切接触者查询
     *
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    List<TtCloseContactDTO> listCloseContacts(String userName, Date startTime, Date endTime);


    /**
     * 查询 个人 接触人数
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countContactNumber(String userId, Date startTime, Date endTime);

    /**
     * 统计 tt_hour_mac
     *
     * @return
     */
    List<Map<String, Object>> countByMacGroupHours( Date contactTime);

}
