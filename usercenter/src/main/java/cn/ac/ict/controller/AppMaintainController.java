package cn.ac.ict.controller;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.entity.SysAppUpgradeEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysAppUpgradeService;
import cn.ac.ict.utils.FileUtil;
import cn.ac.ict.vo.BaseResponse;
import cn.ac.ict.vo.SysAppUpgradeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/app/sys")
@Api(value = "App系统维护接口", tags = {"App系统维护接口"})
public class AppMaintainController {

    @Resource
    private SysAppUpgradeService sysAppUpgradeService;

    @ApiOperation(value = "新增App升级信息——Android")
    @PostMapping(value = "/upgrade/save")
    public BaseResponse<Boolean> saveSysAppUpgrade(SysAppUpgradeVO sysAppUpgradeVO,
                                                   @RequestParam MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new GlobalException(AppCodeInfo.FILE_EMPTY_ERROR);
            }
            // 保存 apk 文件到服务器中
            String path = FileUtil.saveAppUpgradeFile(file);

            // 保存 APP 的升级信息
            SysAppUpgradeEntity sysAppUpgrade = new SysAppUpgradeEntity();
            BeanUtils.copyProperties(sysAppUpgradeVO, sysAppUpgrade);
            sysAppUpgrade.setFileName(file.getOriginalFilename());
            Long fileSize = file.getSize();
            sysAppUpgrade.setSize(fileSize.toString());
            sysAppUpgrade.setType(AppConstant.APP_UPGRADE_TYPE_ANDROID);
            sysAppUpgrade.setDownloadUrl(path);
            Integer result = sysAppUpgradeService.upgrade(sysAppUpgrade, AppConstant.APP_UPGRADE_TYPE_ANDROID);
            if (result > 1) {
                return new BaseResponse(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增app升级]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增app升级]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "获取所有App升级信息——Android")
    @PostMapping(value = "/upgrade/list")
    public BaseResponse<List<SysAppUpgradeEntity>> listSysAppUpgrade() {
        try {
            List<SysAppUpgradeEntity> result = sysAppUpgradeService.listSysAppUpgrade(AppConstant.APP_UPGRADE_TYPE_ANDROID);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[新增app升级]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增app升级]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "新增App升级信息——IOS")
    @PostMapping(value = "/upgrade/saveIOS")
    public BaseResponse<Boolean> saveSysAppUpgradeIOS(SysAppUpgradeVO sysAppUpgradeVO) {
        try {
            // 保存 APP 的升级信息
            SysAppUpgradeEntity sysAppUpgrade = new SysAppUpgradeEntity();
            BeanUtils.copyProperties(sysAppUpgradeVO, sysAppUpgrade);
            sysAppUpgrade.setType(AppConstant.APP_UPGRADE_TYPE_IOS);
            sysAppUpgrade.setFileName("");
            sysAppUpgrade.setSize("");
            sysAppUpgrade.setDownloadUrl("");
            Integer result = sysAppUpgradeService.upgrade(sysAppUpgrade, AppConstant.APP_UPGRADE_TYPE_IOS);
            if (result > 1) {
                return new BaseResponse(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增app升级]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增app升级]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR.getCode(), e.getMessage());
        }
    }


    @ApiOperation(value = "获取所有App升级信息——IOS")
    @PostMapping(value = "/upgrade/listIOS")
    public BaseResponse<List<SysAppUpgradeEntity>> listSysAppUpgradeIOS() {
        try {
            List<SysAppUpgradeEntity> result = sysAppUpgradeService.listSysAppUpgrade(AppConstant.APP_UPGRADE_TYPE_IOS);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[新增app升级]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增app升级]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR.getCode(), e.getMessage());
        }
    }

}
