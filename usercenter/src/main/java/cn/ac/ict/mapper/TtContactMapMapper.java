package cn.ac.ict.mapper;

import cn.ac.ict.dto.TtContactMapDTO;
import cn.ac.ict.dto.TtContactMapNetDTO;
import cn.ac.ict.dto.TtContactMapRespDTO;
import cn.ac.ict.entity.TtContactMapEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TtContactMapMapper extends BaseMapper<TtContactMapEntity> {

    /**
     * 添加/重置 感染的人
     *
     * @param userId
     * @param updateStatus 感染/隔离，0未感染/未隔离，1感染/隔离
     * @param flag         0-修改感染字段，1-修改隔离字段
     * @return
     */
    Integer updateInfectedQuarantineByUserId(@Param("userId") String userId,
                                             @Param("updateStatus") Integer updateStatus,
                                             @Param("flag") Integer flag);

    /**
     * 根据 部门ID、用户姓名、是否感染、是否隔离，模糊查询用户信息
     *
     * @param baId
     * @param userName
     * @param quarantine 是否隔離。0未隔离，1已隔离，2全部
     * @param infected   是否感染。0未感染，1感染，2全部
     * @return
     */
    List<TtContactMapRespDTO> listUserByCondition(@Param("baId") String baId, @Param("userName") String userName,
                                                  @Param("quarantine") Integer quarantine, @Param("infected") Integer infected);

    /**
     * 数据大屏 查询接触网络
     *
     * @param baId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtContactMapDTO> getContactNet(@Param("baId") String baId,
                                               @Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate);


    /**
     * 获取 个人接触网络
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtContactMapNetDTO> listContactNet(@Param("userId") String userId,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);


}
