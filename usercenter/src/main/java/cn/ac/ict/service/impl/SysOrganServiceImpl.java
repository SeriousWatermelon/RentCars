package cn.ac.ict.service.impl;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.entity.SysOrganEntity;
import cn.ac.ict.mapper.SysOrganMapper;
import cn.ac.ict.service.SysOrganService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysOrganServiceImpl extends ServiceImpl<SysOrganMapper, SysOrganEntity> implements SysOrganService {

    @Resource
    private SysOrganMapper sysOrganMapper;

    @Override
    public List<SysOrganEntity> listOrgan(String type) {
        LambdaQueryWrapper<SysOrganEntity> query = Wrappers.lambdaQuery();
        query.eq(!AppConstant.SYS_ORGAN_ALL.equals(type), SysOrganEntity::getType, type);
        List<SysOrganEntity> result = sysOrganMapper.selectList(query);
        return result;
    }
}
