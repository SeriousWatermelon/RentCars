package cn.ac.ict.mapper;

import cn.ac.ict.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 根据用户ID，查询用户角色
     *
     * @param userId
     * @param status 0-正常，1-禁用
     * @return
     */
    List<SysRoleEntity> listRoleByUserId(String userId, String status);
}
