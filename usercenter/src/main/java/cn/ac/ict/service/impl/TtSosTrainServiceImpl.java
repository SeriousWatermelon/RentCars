package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosTrainEntity;
import cn.ac.ict.mapper.TtSosTrainMapper;
import cn.ac.ict.service.TtSosTrainService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TtSosTrainServiceImpl extends ServiceImpl<TtSosTrainMapper, TtSosTrainEntity> implements TtSosTrainService {

    @Resource
    private TtSosTrainMapper ttSosTrainMapper;

    @Override
    public List<TtSosTrainEntity> listTrainInfo(String userId, Date startDate, Date endDate) {
        LambdaQueryWrapper<TtSosTrainEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosTrainEntity::getUserId, userId)
                .between(TtSosTrainEntity::getStartTime, startDate, endDate)
                .orderByAsc(TtSosTrainEntity::getStartTime);
        List<TtSosTrainEntity> result = ttSosTrainMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosTrainEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosTrainEntity::getUserId, userId);
        Integer result = ttSosTrainMapper.delete(query);
        return result;
    }
}
