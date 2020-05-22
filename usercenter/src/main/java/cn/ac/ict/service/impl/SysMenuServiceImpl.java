package cn.ac.ict.service.impl;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.dto.SysMenuDTO;
import cn.ac.ict.entity.SysMenuEntity;
import cn.ac.ict.mapper.SysMenuMapper;
import cn.ac.ict.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Resource
    private SysMenuMapper menuMapper;

    @Override
    public List<SysMenuEntity> listMenuByUserId(String userId) {
        List<SysMenuEntity> result = menuMapper.listMenuByUserId(userId);
        for (int i = 0; i < result.size(); i++) {
            if (ObjectUtils.isEmpty(result.get(i)) || StringUtils.isEmpty(result.get(i).getPermission())) {
                result.remove(i);
            }
        }
        return result;
    }

    @Override
    public List<SysMenuDTO> listMenuByLoginUser(String userId) {
        List<SysMenuDTO> sysMenuDTOList;
        if (userId.equals(AppConstant.SUPER_USER_ID)) {
            sysMenuDTOList = menuMapper.listMenuByLoginUser("0");
        } else {
            sysMenuDTOList = menuMapper.listMenuByLoginUser(userId);
        }
        List<String> menuIds = new ArrayList<>();
        for (SysMenuDTO menu : sysMenuDTOList) {
            if (menu != null && null != menu.getId()) {
                menuIds.add(menu.getId());
            }
        }
        //查询出根菜单
        List<SysMenuDTO> rootMenus = queryMenuByParentId("0", menuIds);
        //递归查询出所有子资源的子资源
        List<SysMenuDTO> treeMenus = getTreeMenus(rootMenus, menuIds);
        return treeMenus;
    }

    /**
     * 根据上级父id，查询出下级所有该用户已授权资源
     *
     * @param parentId 父id
     * @param menuIds  授权资源ids
     * @return
     */
    public List<SysMenuDTO> queryMenuByParentId(String parentId, List<String> menuIds) {
        //根据父Id,查询所有下级资源
        List<SysMenuDTO> menuEntities = menuMapper.listMenuByParentId(parentId);
        List<SysMenuDTO> reMenus = new ArrayList<>();
        for (SysMenuDTO menu : menuEntities) {
            //如果下级资源在用户授权资源中,则添加
            if (menuIds.contains(menu.getId())) {
                reMenus.add(menu);
            }
        }
        return reMenus;
    }

    /**
     * 递归查询出所有菜单的子菜单，子菜单的所有子菜单 只包括用户授权的资源
     *
     * @param resources 源菜单
     * @param menuIds   用户所有授权资源
     * @return
     */
    public List<SysMenuDTO> getTreeMenus(List<SysMenuDTO> resources, List<String> menuIds) {
        List<SysMenuDTO> treeMenus = new ArrayList<>();
        for (SysMenuDTO menu : resources) {
            if (AppConstant.MENU_TYPE_CATALOG.equals(menu.getType())) {
                List<SysMenuDTO> childMenus = queryMenuByParentId(menu.getId(), menuIds);
                menu.setList(getTreeMenus(childMenus, menuIds));
            }
            treeMenus.add(menu);
        }
        return treeMenus;
    }
}
