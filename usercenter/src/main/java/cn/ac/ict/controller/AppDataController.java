package cn.ac.ict.controller;

import cn.ac.ict.entity.*;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.*;
import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.utils.TransferEntityUtil;
import cn.ac.ict.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/app/data")
@Api(value = "APP数据上传和获取接口", tags = {"APP数据上传和获取接口"})
public class AppDataController {

    @Resource
    private TtSosHeartRateService ttSosHeartRateService;

    @Resource
    private TtSosOxygenService ttSosOxygenService;

    @Resource
    private TtSosPressureService ttSosPressureService;

    @Resource
    private TtSosSleepService ttSosSleepService;

    @Resource
    private TtSosSportsService ttSosSportsService;

    @Resource
    private TtSosTrainService ttSosTrainService;

    @Resource
    private AppDataService appDataService;


    @ApiOperation(value = "新增步行")
    @PostMapping(value = "/walk/add")
    public BaseResponse<Boolean> addWalkInfo(@RequestBody AppDataAddVO<TtSosSportsVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosSportsVO> ttSosSportsVOList = appDataAddVO.getDataList();
            List<TtSosSportsEntity> dataList =
                    ttSosSportsVOList.stream().map(((vo) -> TransferEntityUtil.ttSosSportsVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosSportsService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增步行]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增步行]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询步数数据")
    @PostMapping(value = "/walk/list")
    public BaseResponse<List<TtSosSportsVO>> listWalkInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            List<TtSosSportsEntity> ttSosSportsEntityList = ttSosSportsService.listWalkInfo(vo.getUserId(), vo.getStartDate(), vo.getEndDate());
            List<TtSosSportsVO> result =
                    ttSosSportsEntityList.stream().map((entity) -> TransferEntityUtil.ttSosSportsEntity2VO(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询步数数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询步数数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "新增睡眠")
    @PostMapping(value = "/sleep/add")
    public BaseResponse<Boolean> addSleepInfo(@RequestBody AppDataAddVO<TtSosSleepVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosSleepVO> ttSosSleepVOList = appDataAddVO.getDataList();
            List<TtSosSleepEntity> dataList =
                    ttSosSleepVOList.stream().map(((vo) -> TransferEntityUtil.ttSosSleepVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosSleepService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增睡眠]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增睡眠]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询睡眠数据")
    @PostMapping(value = "/sleep/list")
    public BaseResponse<List<TtSosSleepVO>> listSleepInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            String startDate = vo.getStartDate();
            String endDate = vo.getEndDate();
            Date start = DateUtil.parse(startDate);
            Date end = DateUtil.parse(endDate);
            List<TtSosSleepEntity> ttSosSleepEntityList = ttSosSleepService.listSleepInfo(vo.getUserId(), start, end);
            List<TtSosSleepVO> result =
                    ttSosSleepEntityList.stream().map((entity) -> TransferEntityUtil.ttSosSleepEntity2VO(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询睡眠数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询睡眠数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "新增训练")
    @PostMapping(value = "/train/add")
    public BaseResponse<Boolean> addTrainInfo(@RequestBody AppDataAddVO<TtSosTrainVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosTrainVO> ttSosTrainVOList = appDataAddVO.getDataList();

            List<TtSosTrainEntity> dataList =
                    ttSosTrainVOList.stream().map(((vo) -> TransferEntityUtil.ttSosTrainVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosTrainService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增训练]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增训练]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询训练数据")
    @PostMapping(value = "/train/list")
    public BaseResponse<List<TtSosTrainVO>> listTrainInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            String startDate = vo.getStartDate();
            String endDate = vo.getEndDate();
            Date start = DateUtil.parse(startDate);
            Date end = DateUtil.parse(endDate);
            List<TtSosTrainEntity> entityList = ttSosTrainService.listTrainInfo(vo.getUserId(), start, end);
            List<TtSosTrainVO> result =
                    entityList.stream().map((entity) -> TransferEntityUtil.ttSosTrainEntity2Vo(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询训练数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询训练数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "新增心率")
    @PostMapping(value = "/bmp/add")
    public BaseResponse<Boolean> addBmpInfo(@RequestBody AppDataAddVO<TtSosHeartRateVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosHeartRateVO> ttSosHeartRateVOList = appDataAddVO.getDataList();

            List<TtSosHeartRateEntity> dataList =
                    ttSosHeartRateVOList.stream().map(((vo) -> TransferEntityUtil.ttSosHeartRateVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosHeartRateService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.SYS_ERROR, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增心率]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增心率]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询心率数据")
    @PostMapping(value = "/bmp/list")
    public BaseResponse<List<TtSosHeartRateVO>> listBmpInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            String startDate = vo.getStartDate();
            String endDate = vo.getEndDate();
            Date start = DateUtil.parse(startDate);
            Date end = DateUtil.parse(endDate);
            List<TtSosHeartRateEntity> entityList = ttSosHeartRateService.listBmpInfo(vo.getUserId(), start, end);
            List<TtSosHeartRateVO> result =
                    entityList.stream().map((entity) -> TransferEntityUtil.ttSosHeartRateEntity2VO(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询心率数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询心率数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "新增血压")
    @PostMapping(value = "/pressure/add")
    public BaseResponse<Boolean> addPressureInfo(@RequestBody AppDataAddVO<TtSosPressureVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosPressureVO> ttSosPressureVOList = appDataAddVO.getDataList();

            List<TtSosPressureEntity> dataList =
                    ttSosPressureVOList.stream().map(((vo) -> TransferEntityUtil.ttSosPressVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosPressureService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增血压]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增血压]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询血压数据")
    @PostMapping(value = "/pressure/list")
    public BaseResponse<List<TtSosPressureVO>> listPressureInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            String startDate = vo.getStartDate();
            String endDate = vo.getEndDate();
            Date start = DateUtil.parse(startDate);
            Date end = DateUtil.parse(endDate);
            List<TtSosPressureEntity> entityList = ttSosPressureService.listPressureInfo(vo.getUserId(), start, end);
            List<TtSosPressureVO> result =
                    entityList.stream().map((entity) -> TransferEntityUtil.ttSosPressureEntity2VO(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询血压数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询血压数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "新增血氧")
    @PostMapping(value = "/oxygen/add")
    public BaseResponse<Boolean> addOxygenInfo(@RequestBody AppDataAddVO<TtSosOxygenVO> appDataAddVO) {
        try {
            checkAddParam(appDataAddVO);
            String userId = appDataAddVO.getUserId();
            List<TtSosOxygenVO> ttSosOxygenVOList = appDataAddVO.getDataList();

            List<TtSosOxygenEntity> dataList =
                    ttSosOxygenVOList.stream().map(((vo) -> TransferEntityUtil.ttSosOxygenVO2Entity(userId, vo))).collect(Collectors.toList());
            Boolean result = ttSosOxygenService.saveBatch(dataList);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[新增血氧]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[新增血氧]失败 userId：{}，原因：{}", appDataAddVO.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "查询血氧数据")
    @PostMapping(value = "/oxygen/list")
    public BaseResponse<List<TtSosOxygenVO>> listOxygenInfo(@RequestBody AppDataQueryVO vo) {
        try {
            checkQueryParam(vo);
            String startDate = vo.getStartDate();
            String endDate = vo.getEndDate();
            Date start = DateUtil.parse(startDate);
            Date end = DateUtil.parse(endDate);
            List<TtSosOxygenEntity> entityList = ttSosOxygenService.listOxygenInfo(vo.getUserId(), start, end);
            List<TtSosOxygenVO> result =
                    entityList.stream().map((entity) -> TransferEntityUtil.ttSosOxygenEntity2VO(entity)).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询血氧数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询血氧数据]失败 userId：{}，原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "清空历史数据")
    @PostMapping(value = "/history/clear")
    public BaseResponse<Boolean> clearHistory(@RequestParam("userId") String userId) {
        try {
            Boolean result = appDataService.clearHistory(userId);
            if (result) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[清空历史数据]失败 userId：{}，原因：{}", userId, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[清空历史数据]失败 userId：{}，原因：{}", userId, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    /**
     * 校验 新增参数
     */
    private void checkAddParam(AppDataAddVO appDataAddVO) {
        if (StringUtils.isEmpty(appDataAddVO.getUserId())) {
            throw new GlobalException(AppCodeInfo.USER_ID_EMPTY);
        }
        if (ObjectUtils.isEmpty(appDataAddVO.getDataList())) {
            throw new GlobalException(AppCodeInfo.DATA_LIST_EMPTY);
        }
    }

    /**
     * 校验 查询参数
     */
    private void checkQueryParam(AppDataQueryVO vo) {
        if (StringUtils.isEmpty(vo.getUserId())) {
            throw new GlobalException(AppCodeInfo.USER_ID_EMPTY);
        }
        // 开始时间 不能 大于等于 结束时间
        int check = vo.getStartDate().compareTo(vo.getEndDate());
        if (check >= 0) {
            throw new GlobalException(AppCodeInfo.START_END_TIME_ERROR);
        }
    }


}
