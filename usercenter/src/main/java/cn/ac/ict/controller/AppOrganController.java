package cn.ac.ict.controller;

import cn.ac.ict.entity.SysOrganEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.SysOrganService;
import cn.ac.ict.vo.BaseResponse;
import cn.ac.ict.vo.SysOrganVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/app/organ")
@Api(value = "App组织部门", tags = {"App组织部门"})
public class AppOrganController {

    @Resource
    private SysOrganService sysOrganService;

    @ApiOperation(value = "查询部门机构")
    @PostMapping(value = "/listOrgan")
    public BaseResponse<List<SysOrganVO>> listOrgan(@ApiParam(value = "类型：-1-全部，1-机构，2-机构下的部门")
                                                    @RequestParam(value = "type", defaultValue = "-1") String type) {
        try {
            List<SysOrganEntity> entityList = sysOrganService.listOrgan(type);
            List<SysOrganVO> result = entityList.stream().map(entity -> {
                SysOrganVO vo = new SysOrganVO();
                vo.setBaId(entity.getId());
                vo.setName(entity.getName());
                return vo;
            }).collect(Collectors.toList());
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询部门机构]失败,原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询部门机构]失败,原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
