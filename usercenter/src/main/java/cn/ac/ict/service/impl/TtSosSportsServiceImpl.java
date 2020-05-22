package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtSosSportsEntity;
import cn.ac.ict.mapper.TtSosSportsMapper;
import cn.ac.ict.service.TtSosSportsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TtSosSportsServiceImpl extends ServiceImpl<TtSosSportsMapper, TtSosSportsEntity> implements TtSosSportsService {

    @Resource
    private TtSosSportsMapper ttSosSportsMapper;


    @Override
    public List<TtSosSportsEntity> listWalkInfo(String userId, String startDate, String endDate) {
        LambdaQueryWrapper<TtSosSportsEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosSportsEntity::getUserId, userId)
                .between(TtSosSportsEntity::getSportTime, startDate, endDate)
                .orderByAsc(TtSosSportsEntity::getSportTime);
        List<TtSosSportsEntity> result = ttSosSportsMapper.selectList(query);
        return result;
    }

    @Transactional
    @Override
    public Integer deleteByUserId(String userId) {
        LambdaQueryWrapper<TtSosSportsEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosSportsEntity::getUserId, userId);
        Integer result = ttSosSportsMapper.delete(query);
        return result;
    }
}
