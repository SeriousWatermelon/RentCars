package cn.ac.ict.service.impl;

import cn.ac.ict.entity.SysRoleEntity;
import cn.ac.ict.mapper.SysRoleMapper;
import cn.ac.ict.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRoleEntity> listRoleByUserId(String userId, String status) {
        return roleMapper.listRoleByUserId(userId, status);
    }
}
