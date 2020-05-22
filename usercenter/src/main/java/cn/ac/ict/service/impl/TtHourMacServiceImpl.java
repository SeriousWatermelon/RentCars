package cn.ac.ict.service.impl;

import cn.ac.ict.entity.TtHourMacEntity;
import cn.ac.ict.mapper.TtHourMacMapper;
import cn.ac.ict.service.TtHourMacService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TtHourMacServiceImpl extends ServiceImpl<TtHourMacMapper, TtHourMacEntity> implements TtHourMacService {

    @Resource
    private TtHourMacMapper ttHourMacMapper;

    @Override
    public Integer deleteByContactTime(Date contactTime) {
        LambdaQueryWrapper<TtHourMacEntity> query = Wrappers.lambdaQuery();
        query.eq(TtHourMacEntity::getContactTime, contactTime);
        return ttHourMacMapper.delete(query);
    }

    /**
     * 添加 5s的误差值
     *
     * @param contactTime
     * @return
     */
    @Override
    public List<Map<String, Object>> countByContactTime(Date contactTime) {
        return ttHourMacMapper.countBetweenContactTime(new Date(contactTime.getTime() - 5000), new Date(contactTime.getTime() + 5000));
    }
}
