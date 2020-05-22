package cn.ac.ict.service.impl;

import cn.ac.ict.entity.SysTenantInfoEntity;
import cn.ac.ict.mapper.SysTenantInfoMapper;
import cn.ac.ict.service.SysTenantInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysTenantInfoServiceImpl extends ServiceImpl<SysTenantInfoMapper, SysTenantInfoEntity> implements SysTenantInfoService {

    @Resource
    private SysTenantInfoMapper tenantInfoMapper;

}
