package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosSleepEntity;
import cn.ac.ict.mapper.TtSosSleepMapper;
import cn.ac.ict.service.TtSosSleepService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TtSosSleepServiceImpl extends ServiceImpl<TtSosSleepMapper, TtSosSleepEntity> implements TtSosSleepService {

    @Resource
    private TtSosSleepMapper ttSosSleepMapper;

    @Override
    public List<TtSosSleepEntity> listSleepInfo(String userId, Date startDate, Date endDate) {
        LambdaQueryWrapper<TtSosSleepEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosSleepEntity::getUserId, userId)
                .between(TtSosSleepEntity::getStartTime, startDate, endDate)
                .orderByAsc(TtSosSleepEntity::getStartTime);
        List<TtSosSleepEntity> result = ttSosSleepMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosSleepEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosSleepEntity::getUserId, userId);
        Integer result = ttSosSleepMapper.delete(query);
        return result;
    }
}
