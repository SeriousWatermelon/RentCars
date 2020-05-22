package cn.ac.ict.controller;

import cn.ac.ict.dto.SysUserDTO;
import cn.ac.ict.entity.SysUserEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysUserService;
import cn.ac.ict.vo.BaseResponse;
import cn.ac.ict.vo.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户登录、注册 信息修改等
 */

@Slf4j
@RestController
@RequestMapping("/app/user")
@Api(value = "App用户信息与登录注册", tags = {"App用户信息与登录注册"})
public class AppUserCenterController {

    @Resource
    private SysUserService sysUserService;


    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    public BaseResponse<SysUserVO> appLogin(@RequestParam(value = "phone") String phone,
                                            @RequestParam(value = "password") String password) {
        try {
            SysUserDTO dto = sysUserService.appLogin(phone, password);
            SysUserVO result = new SysUserVO();
            BeanUtils.copyProperties(dto, result);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[用户登录]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[用户登录]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public BaseResponse<Boolean> appRegister(@RequestParam(value = "phone") String phone,
                                             @RequestParam(value = "password") String password) {
        try {
            Boolean result = sysUserService.appRegister(phone, password);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[用户注册]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[用户注册]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "根据用户ID查询用户信息")
    @PostMapping(value = "/getUserByUserId")
    public BaseResponse<SysUserVO> getUserByUserId(@RequestParam(value = "userId") String userId) {
        try {
            SysUserDTO dto = sysUserService.getUserByUserId(userId);
            SysUserVO result = new SysUserVO();
            BeanUtils.copyProperties(dto, result);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询用户信息]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询用户信息]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "根据部门ID或者用户姓名 查询用户信息")
    @PostMapping(value = "/listUserByBaIdAndUserName")
    public BaseResponse<List<SysUserVO>> listUserByBaIdAndUserName(@RequestParam(value = "baId", required = false) String baId,
                                                                   @RequestParam(value = "userName", required = false) String userName) {
        try {
            List<SysUserDTO> sysUserDTOList = sysUserService.listUserByBaIdAndUserName(baId, userName);
            List<SysUserVO> result = sysUserDTOList.stream().map(dto -> {
                SysUserVO vo = new SysUserVO();
                BeanUtils.copyProperties(dto, vo);
                return vo;
            }).collect(Collectors.toList());
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询用户信息]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询用户信息]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "修改登录密码")
    @PostMapping(value = "/updatePassword")
    public BaseResponse<Boolean> updatePassword(@RequestParam(value = "phone") String phone,
                                                @RequestParam(value = "password") String password) {
        try {
            Boolean result = sysUserService.updatePassword(phone, password);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[修改密码]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[修改密码]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "完善用户信息")
    @PostMapping(value = "/updateUser")
    public BaseResponse<Boolean> updateUser(@RequestBody SysUserVO sysUserVO) {
        try {
            SysUserEntity sysUserEntity = new SysUserEntity();
            BeanUtils.copyProperties(sysUserVO, sysUserEntity);
            Boolean result = sysUserService.updateUser(sysUserEntity);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[修改用户信息]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[修改用户信息]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
