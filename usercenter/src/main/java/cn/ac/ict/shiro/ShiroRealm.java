package cn.ac.ict.shiro;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.entity.SysMenuEntity;
import cn.ac.ict.entity.SysRoleEntity;
import cn.ac.ict.entity.SysTenantInfoEntity;
import cn.ac.ict.entity.SysUserEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysMenuService;
import cn.ac.ict.service.SysRoleService;
import cn.ac.ict.service.SysTenantInfoService;
import cn.ac.ict.service.SysUserService;
import cn.ac.ict.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * shiro用户身份验证 权限验证
 *
 * @author weiman cui
 * @date 2020/5/20 16:20
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private SysUserService userService;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysTenantInfoService tenantInfoService;


    /**
     * 用户权限验证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        SysUserEntity user = (SysUserEntity) principalCollection.getPrimaryPrincipal();
        if (!ObjectUtils.isEmpty(user)) {
            // 将用户 所有的 可用角色 加入到 SimpleAuthorizationInfo 中
            List<SysRoleEntity> roleEntityList = roleService.listRoleByUserId(user.getId(), AppConstant.ROLE_STATUS_ORDINARY);
            roleEntityList.forEach(role -> info.addRole(role.getId()));

            // 查询角色对应的菜单，将菜单权限添加到 SimpleAuthorizationInfo 中,超级租户管理员，那么拥有全部权限
            SysTenantInfoEntity tenantInfoEntity = tenantInfoService.getById(user.getTenantId());
            List<SysMenuEntity> menuEntityList;
            if (AppConstant.TENANT_TYPE_SUPER == tenantInfoEntity.getTenantType()) {
                menuEntityList = menuService.list();
            } else {
                menuEntityList = menuService.listMenuByUserId(user.getId());
            }
            Set<String> permissionSet = new HashSet<>();
            menuEntityList.forEach(menu -> permissionSet.add(menu.getPermission()));
            info.addStringPermissions(permissionSet);
        }
        return info;
    }

    /**
     * 用户身份验证
     *
     * @param authenticationToken
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        String loginName = (String) authenticationToken.getPrincipal();
        SysUserEntity sysUserEntity = userService.getUserByLoginName(loginName);
        if (ObjectUtils.isEmpty(sysUserEntity)) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_PASS_ERROR);
        }
        if (AppConstant.ACCOUNT_STATUS_FORBIDDEN.equals(sysUserEntity.getStatus())) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_FORBIDDEN);
        }
        SysTenantInfoEntity tenantInfoEntity = tenantInfoService.getById(sysUserEntity.getTenantId());
        if (ObjectUtils.isEmpty(tenantInfoEntity)) {
            throw new GlobalException(AppCodeInfo.TENANT_NOT_EXISTS);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUserEntity, sysUserEntity.getPassWord(), ByteSource.Util.bytes(sysUserEntity.getSalt()), getName());
        return authenticationInfo;
    }

    /**
     * 配置 加密算法 和 散列次数
     *
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.algorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}















