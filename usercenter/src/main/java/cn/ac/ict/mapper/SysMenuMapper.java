package cn.ac.ict.mapper;

import cn.ac.ict.dto.SysMenuDTO;
import cn.ac.ict.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {

    /**
     * 根据用户ID 查询用户权限
     *
     * @param userId
     * @return
     */
    List<SysMenuEntity> listMenuByUserId(String userId);

    /**
     * 查询 已登录用户的菜单
     *
     * @param userId
     * @return
     */
    List<SysMenuDTO> listMenuByLoginUser(String userId);

    /**
     * 根据父菜单Id查询菜单
     *
     * @param parenId
     * @return
     */
    List<SysMenuDTO> listMenuByParentId(String parenId);

}
