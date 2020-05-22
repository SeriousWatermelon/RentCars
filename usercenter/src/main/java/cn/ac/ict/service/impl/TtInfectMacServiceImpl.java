package cn.ac.ict.service.impl;

import cn.ac.ict.dto.TtCloseContactDTO;
import cn.ac.ict.entity.TtInfectMacEntity;
import cn.ac.ict.mapper.TtInfectMacMapper;
import cn.ac.ict.service.TtInfectMacService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TtInfectMacServiceImpl extends ServiceImpl<TtInfectMacMapper, TtInfectMacEntity> implements TtInfectMacService {

    @Resource
    private TtInfectMacMapper ttInfectMacMapper;

    @Override
    public List<TtCloseContactDTO> listCloseContacts(String userName, Date startTime, Date endTime) {
        List<TtCloseContactDTO> result = ttInfectMacMapper.listCloseContacts(userName, startTime, endTime);
        return result;
    }

    @Override
    public List<Map<String, Object>> countContactNumber(String userId, Date startTime, Date endTime) {
        return ttInfectMacMapper.countContactNumber(userId, startTime, endTime);
    }

    @Override
    public List<Map<String, Object>> countByMacGroupHours(Date contactTime) {
        return ttInfectMacMapper.countByMacGroupHours(contactTime);
    }
}
