package cn.ac.ict.service.impl;

import cn.ac.ict.entity.SysAppUpgradeEntity;
import cn.ac.ict.mapper.SysAppUpgradeMapper;
import cn.ac.ict.service.SysAppUpgradeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysAppUpgradeServiceImpl extends ServiceImpl<SysAppUpgradeMapper, SysAppUpgradeEntity> implements SysAppUpgradeService {

    @Resource
    private SysAppUpgradeMapper sysAppUpgradeMapper;

    @Transactional
    @Override
    public Integer upgrade(SysAppUpgradeEntity sysAppUpgrade, Integer type) {
        // 删除之前的版本信息
        LambdaQueryWrapper<SysAppUpgradeEntity> deleteQuery = Wrappers.lambdaQuery();
        deleteQuery.eq(SysAppUpgradeEntity::getType, type);
        Integer deleteResult = sysAppUpgradeMapper.delete(deleteQuery);

        // 保存 新的版本信息
        Integer saveResult = sysAppUpgradeMapper.insert(sysAppUpgrade);
        return deleteResult + saveResult;
    }

    @Override
    public List<SysAppUpgradeEntity> listSysAppUpgrade(Integer type) {
        LambdaQueryWrapper<SysAppUpgradeEntity> query = Wrappers.lambdaQuery();
        query.eq(SysAppUpgradeEntity::getType, type);
        List<SysAppUpgradeEntity> result = sysAppUpgradeMapper.selectList(query);
        return result;
    }
}
