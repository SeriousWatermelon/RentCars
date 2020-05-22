package cn.ac.ict.service;

import cn.ac.ict.dto.TtContactMapDTO;
import cn.ac.ict.dto.TtContactMapNetDTO;
import cn.ac.ict.dto.TtContactMapRespDTO;
import cn.ac.ict.entity.TtContactMapEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TtContactMapService extends IService<TtContactMapEntity> {

    /**
     * 添加/重置 感染的人
     *
     * @param userId
     * @param updateStatus 感染/隔离，0未感染/未隔离，1感染/隔离
     * @param flag         0-修改感染字段，1-修改隔离字段
     * @return
     */
    Integer updateInfectedQuarantineByUserId(String userId, Integer updateStatus, Integer flag);


    /**
     * 根据 部门ID、用户姓名、是否感染、是否隔离，模糊查询用户信息
     *
     * @param baId
     * @param userName
     * @param quarantine 是否隔離。0未隔离，1已隔离，2全部
     * @param infected   是否感染。0未感染，1感染，2全部
     * @return
     */
    List<TtContactMapRespDTO> listUserByCondition(String baId, String userName, Integer quarantine, Integer infected);


    /**
     * 数据大屏 查询接触网络
     *
     * @param baId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtContactMapDTO> getContactNet(String baId, Date startDate, Date endDate);


    /**
     * 获取 个人接触网络
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    List<TtContactMapNetDTO> listContactNet(String userId, Date startDate, Date endDate);

    /**
     * 根据接触时间删除
     *
     * @param contactTime
     * @return
     */
    Integer deleteByContactTime(Date contactTime);

    /**
     * 查询 接触网咯 有数据的 最近一天 日期
     *
     * @return
     */
    Map<String, String> getContactNetDate();
}
