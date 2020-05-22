package cn.ac.ict.controller;

import cn.ac.ict.entity.TtSosBindEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.TtSosBindService;
import cn.ac.ict.vo.BaseResponse;
import cn.ac.ict.vo.TtSosBindVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/app/watch")
@Api(value = "APP与手环绑定相关接口", tags = {"APP与手环绑定相关接口"})
public class AppBindWatchController {

    @Resource
    private TtSosBindService ttSosBindService;

    @ApiOperation(value = "查询用户绑定的手环")
    @PostMapping(value = "/getMacByUserId")
    public BaseResponse<TtSosBindEntity> getMacByUserId(@RequestParam("userId") String userId) {
        try {
            if (StringUtils.isEmpty(userId)) {
                throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
            }
            TtSosBindEntity ttSosBindEntity = ttSosBindService.getUserMacByUserId(userId);
            return new BaseResponse(AppCodeInfo.SUCCESS, ttSosBindEntity);
        } catch (GlobalException e) {
            log.info("[查询用户绑定的手环]失败 userId：{}，原因：{}", userId, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询用户绑定的手环]失败 userId：{}，原因：{}", userId, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "用户手环绑定")
    @PostMapping(value = "/bind")
    public BaseResponse<Boolean> userMacBind(@RequestBody TtSosBindVO vo) {
        try {
            if (StringUtils.isEmpty(vo.getUserId()) || StringUtils.isEmpty(vo.getMac())) {
                throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
            }
            TtSosBindEntity entity = new TtSosBindEntity();
            BeanUtils.copyProperties(vo, entity);
            Boolean result = ttSosBindService.userMacBind(entity);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[用户手环绑定]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[用户手环绑定]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "用户手环解绑")
    @PostMapping(value = "/userMacUnBind")
    public BaseResponse<Boolean> userMacUnBind(@RequestBody TtSosBindVO vo) {
        try {
            if (StringUtils.isEmpty(vo.getUserId()) || StringUtils.isEmpty(vo.getMac())) {
                throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
            }
            TtSosBindEntity entity = new TtSosBindEntity();
            BeanUtils.copyProperties(vo, entity);
            Boolean result = ttSosBindService.userMacUnBind(entity);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[用户手环解绑]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[用户手环解绑]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

}
