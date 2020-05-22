package cn.ac.ict.controller;

import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysMenuService;
import cn.ac.ict.vo.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/om/role")
@Api(value = "运维系统角色管理", tags = {"运维系统角色管理"})
public class OMRoleController {

    @Resource
    private SysMenuService menuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public BaseResponse list(@RequestParam Map<String, Object> params) {
        try {
            System.out.println(params);
            return new BaseResponse(AppCodeInfo.SUCCESS, params);
        } catch (GlobalException e) {
            log.info("[获取角色列表]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取角色列表]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
