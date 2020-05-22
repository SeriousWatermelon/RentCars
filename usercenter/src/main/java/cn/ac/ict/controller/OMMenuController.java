package cn.ac.ict.controller;

import cn.ac.ict.dto.SysMenuDTO;
import cn.ac.ict.entity.SysMenuEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysMenuService;
import cn.ac.ict.utils.ShiroUtil;
import cn.ac.ict.vo.BaseResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/om/menu")
@Api(value = "运维系统菜单管理", tags = {"运维系统菜单管理"})
public class OMMenuController {

    @Resource
    private SysMenuService menuService;

    @RequestMapping(value = "/userMenu")
    public BaseResponse<List<SysMenuEntity>> userMenu() {
        try {
            List<SysMenuDTO> menuDTOList = menuService.listMenuByLoginUser(ShiroUtil.getUserId());
            return new BaseResponse(AppCodeInfo.SUCCESS, menuDTOList);
        } catch (GlobalException e) {
            log.info("[获取用户权限]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取用户权限]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
