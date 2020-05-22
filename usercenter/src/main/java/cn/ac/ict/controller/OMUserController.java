package cn.ac.ict.controller;

import cn.ac.ict.entity.SysUserEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysUserService;
import cn.ac.ict.utils.ShiroUtil;
import cn.ac.ict.vo.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Operation and Maintenance 运维系统——用户中心
 */

@Slf4j
@RestController
@RequestMapping("/om/user")
@Api(value = "运维系统用户角色管理", tags = {"运维系统用户角色管理"})
public class OMUserController {

    @Resource
    private SysUserService userService;

    @RequestMapping("/info")
    public BaseResponse info() {
        try {
            String userId = ShiroUtil.getUserEntity().getId();
            SysUserEntity user = userService.getById(userId);
            if (user != null) {
                user.setPassWord("");
            }
            return new BaseResponse(AppCodeInfo.SUCCESS, user);
        } catch (GlobalException e) {
            log.info("[获取用户信息]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取用户信息]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
