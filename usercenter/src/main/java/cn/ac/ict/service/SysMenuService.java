package cn.ac.ict.service;

import cn.ac.ict.dto.SysMenuDTO;
import cn.ac.ict.entity.SysMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限 管理服务
 *
 * @author weiman cui
 * @date 2020/5/20 16:16
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据用户ID 查询用户权限
     *
     * @param userId
     * @return
     */
    List<SysMenuEntity> listMenuByUserId(String userId);

    /**
     * 根据当前登录用户的ID，查询出菜单树
     *
     * @param userId
     * @return
     */
    List<SysMenuDTO> listMenuByLoginUser(String userId);

}
