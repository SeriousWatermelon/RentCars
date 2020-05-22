package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosPressureEntity;
import cn.ac.ict.mapper.TtSosPressureMapper;
import cn.ac.ict.service.TtSosPressureService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TtSosPressureServiceImpl extends ServiceImpl<TtSosPressureMapper, TtSosPressureEntity> implements TtSosPressureService {

    @Resource
    private TtSosPressureMapper ttSosPressureMapper;

    @Override
    public List<TtSosPressureEntity> listPressureInfo(String userId, Date startDate, Date endDate) {
        LambdaQueryWrapper<TtSosPressureEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosPressureEntity::getUserId, userId)
                .between(TtSosPressureEntity::getCreateTime, startDate, endDate)
                .orderByAsc(TtSosPressureEntity::getCreateTime);
        List<TtSosPressureEntity> result = ttSosPressureMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosPressureEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosPressureEntity::getUserId, userId);
        Integer result = ttSosPressureMapper.delete(query);
        return result;
    }
}
