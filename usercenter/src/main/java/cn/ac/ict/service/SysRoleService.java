package cn.ac.ict.service;

import cn.ac.ict.entity.SysRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色管理
 *
 * @author weiman cui
 * @date 2020/5/20 16:15
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 根据用户ID，查询用户角色
     *
     * @param userId
     * @param status 0-正常，1-禁用
     * @return
     */
    List<SysRoleEntity> listRoleByUserId(String userId, String status);

}
