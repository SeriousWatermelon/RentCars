package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosHeartRateEntity;
import cn.ac.ict.mapper.TtSosHeartRateMapper;
import cn.ac.ict.service.TtSosHeartRateService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TtSosHeartRateServiceImpl extends ServiceImpl<TtSosHeartRateMapper, TtSosHeartRateEntity> implements TtSosHeartRateService {

    @Resource
    private TtSosHeartRateMapper ttSosHeartRateMapper;

    @Override
    public List<TtSosHeartRateEntity> listBmpInfo(String userId, Date startDate, Date endDate) {
        LambdaQueryWrapper<TtSosHeartRateEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosHeartRateEntity::getUserId, userId)
                .between(TtSosHeartRateEntity::getCreateTime, startDate, endDate)
                .orderByAsc(TtSosHeartRateEntity::getCreateTime);
        List<TtSosHeartRateEntity> result = ttSosHeartRateMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosHeartRateEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosHeartRateEntity::getUserId, userId);
        Integer result = ttSosHeartRateMapper.delete(query);
        return result;
    }
}
