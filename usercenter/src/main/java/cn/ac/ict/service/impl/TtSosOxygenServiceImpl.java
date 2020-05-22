package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosOxygenEntity;
import cn.ac.ict.mapper.TtSosOxygenMapper;
import cn.ac.ict.service.TtSosOxygenService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TtSosOxygenServiceImpl extends ServiceImpl<TtSosOxygenMapper, TtSosOxygenEntity> implements TtSosOxygenService {

    @Resource
    private TtSosOxygenMapper ttSosOxygenMapper;

    @Override
    public List<TtSosOxygenEntity> listOxygenInfo(String userId, Date startDate, Date endDate) {
        LambdaQueryWrapper<TtSosOxygenEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosOxygenEntity::getUserId, userId)
                .between(TtSosOxygenEntity::getDateTime, startDate, endDate)
                .orderByAsc(TtSosOxygenEntity::getCreateTime);
        List<TtSosOxygenEntity> result = ttSosOxygenMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosOxygenEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosOxygenEntity::getUserId, userId);
        Integer result = ttSosOxygenMapper.delete(query);
        return result;
    }
}
