package cn.ac.ict.service.impl;

import cn.ac.ict.dto.TtContactMapDTO;
import cn.ac.ict.dto.TtContactMapNetDTO;
import cn.ac.ict.dto.TtContactMapRespDTO;
import cn.ac.ict.entity.TtContactMapEntity;
import cn.ac.ict.mapper.TtContactMapMapper;
import cn.ac.ict.service.TtContactMapService;
import cn.ac.ict.utils.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TtContactMapServiceImpl extends ServiceImpl<TtContactMapMapper, TtContactMapEntity> implements TtContactMapService {

    @Resource
    private TtContactMapMapper ttContactMapMapper;

    @Transactional
    @Override
    public Integer updateInfectedQuarantineByUserId(String userId, Integer updateStatus, Integer flag) {
        Integer result = ttContactMapMapper.updateInfectedQuarantineByUserId(userId, updateStatus, flag);
        return result;
    }

    @Override
    public List<TtContactMapRespDTO> listUserByCondition(String baId, String userName, Integer quarantine, Integer infected) {
        List<TtContactMapRespDTO> result = ttContactMapMapper.listUserByCondition(baId, userName, quarantine, infected);
        return result;
    }

    @Override
    public List<TtContactMapDTO> getContactNet(String baId, Date startDate, Date endDate) {
        return ttContactMapMapper.getContactNet(baId, startDate, endDate);
    }

    @Override
    public List<TtContactMapNetDTO> listContactNet(String userId, Date startDate, Date endDate) {
        return ttContactMapMapper.listContactNet(userId, startDate, endDate);
    }

    /**
     * 添加 5s的误差值
     *
     * @param contactTime
     * @return
     */
    @Override
    public Integer deleteByContactTime(Date contactTime) {
        LambdaQueryWrapper<TtContactMapEntity> query = Wrappers.lambdaQuery();
        query.between(TtContactMapEntity::getContactTime, new Date(contactTime.getTime() - 5000), new Date(contactTime.getTime() + 5000));
        return ttContactMapMapper.delete(query);
    }

    @Override
    public Map<String, String> getContactNetDate() {
        Map<String, String> map = new HashMap<>(2);
        LambdaQueryWrapper<TtContactMapEntity> query = Wrappers.lambdaQuery();
        query.orderByDesc(TtContactMapEntity::getContactTime);
        List<TtContactMapEntity> entityList = ttContactMapMapper.selectList(query);
        if (entityList.size() > 0) {
            Date contactTime = entityList.get(0).getContactTime();
            String startTime = DateUtil.dateFormat(contactTime, DateUtil.beginSdf);
            String endTime = DateUtil.dateFormat(contactTime, DateUtil.endSdf);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
        }
        return map;
    }

}
