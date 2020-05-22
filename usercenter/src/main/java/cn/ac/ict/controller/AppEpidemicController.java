package cn.ac.ict.controller;

import cn.ac.ict.config.JNAConfig;
import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.dto.TtCloseContactDTO;
import cn.ac.ict.dto.TtContactMapDTO;
import cn.ac.ict.dto.TtContactMapNetDTO;
import cn.ac.ict.dto.TtContactMapRespDTO;
import cn.ac.ict.entity.*;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.service.*;
import cn.ac.ict.utils.CalculateUtil;
import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.utils.ExcelUtil;
import cn.ac.ict.vo.*;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/app/net")
@Api(value = "Sos大屏App接口", tags = {"Sos大屏App接口"})
public class AppEpidemicController {

    @Resource
    private TtContactMapService ttContactMapService;

    @Resource
    private TtInfectMacService ttInfectMacService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private AppEpidemicService appEpidemicService;

    @ApiOperation(value = "上传mac地址")
    @PostMapping(value = "/macInfo/v1")
    public BaseResponse<List<TtInfectMacRespVO>> macInfoPost(@ApiParam(required = true) @RequestBody BraceletInfoVO braceletInfo) {
        try {
            SysUserEntity sysUserEntity = sysUserService.getById(braceletInfo.getUserId());
            if (ObjectUtils.isEmpty(sysUserEntity)) {
                throw new GlobalException(AppCodeInfo.ACCOUNT_NOT_EXISTS);
            }
            List<TtInfectMacRespVO> result = appEpidemicService.macInfoPost(braceletInfo);
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[上传mac地址]失败  原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[上传mac地址]失败  原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "获取个人接触网络-大屏")
    @PostMapping(value = "/contractNet/v1")
    public BaseResponse<Map<String, Object>> listContactNetScreen(@RequestBody AppDataQueryVO vo) {
        try {
            Map<String, Object> result = new HashMap<>();
            List<TtContactMapNetDTO> dtoList = ttContactMapService.listContactNet(vo.getUserId(), DateUtil.parse(vo.getStartDate()), DateUtil.parse(vo.getEndDate()));
            if (dtoList.size() > 0) {
                result.put("userId", dtoList.get(0).getUserId());
                result.put("userName", dtoList.get(0).getUserName());
                List<TtContactMapNetVO> childData = new ArrayList<>();
                dtoList.forEach(dto -> {
                    // 一级接触人对象
                    TtContactMapNetVO netVO = ttContactMapNetDTO2VO(dto);
                    // 获取 二级接触人
                    /*List<TtContactMapNetDTO> childDataDTOList = ttContactMapService.listContactNet(dto.getTargetId(), DateUtil.parse(vo.getStartDate()), DateUtil.parse(vo.getEndDate()));
                    List<TtContactMapNetVO> childDataVOList = new ArrayList<>();
                    for (TtContactMapNetDTO ttContactMapNetDTO : childDataDTOList) {
                        // 若二级接触人 是 用户本人，则不列入 二级接触人的 childData中
                        if (ttContactMapNetDTO.getTargetId().equals(vo.getUserId())) {
                            continue;
                        }
                        TtContactMapNetVO childNetVO = ttContactMapNetDTO2VO(ttContactMapNetDTO);
                        childDataVOList.add(childNetVO);
                    }
                    if (childDataVOList.size() > 0) {
                        netVO.setChildData(childDataVOList);
                    }*/
                    childData.add(netVO);
                });
                result.put("childData", childData);
            }
            if (result.size() <= 0) {
                result = null;
            }
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[获取个人接触网络-大屏]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取个人接触网络-大屏]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "获取个人接触网络-App")
    @PostMapping(value = "/contractNet/v2")
    public BaseResponse<List<Map<String, Object>>> listContactNetApp(@RequestBody AppDataQueryVO vo) {
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            List<TtContactMapNetDTO> dtoList = ttContactMapService.listContactNet(vo.getUserId(), DateUtil.parse(vo.getStartDate()), DateUtil.parse(vo.getEndDate()));
            if (dtoList.size() == 0) {
                return new BaseResponse(AppCodeInfo.SUCCESS, result);
            }
            // 按照时间 分组
            dtoList.forEach(dto -> dto.setHhTime(DateUtil.dateFormatHH(dto.getContactTime())));
            Map<String, List<TtContactMapNetDTO>> collect = dtoList.stream().collect(Collectors.groupingBy(TtContactMapNetDTO::getHhTime));
            collect.forEach((k, v) -> {
                Map<String, Object> map = new HashMap<>(2);
                map.put("hour", DateUtil.dateFormat(DateUtil.parse(k), DateUtil.onlyHourSdf));
                List<Map<String, Object>> child = new ArrayList<>(8);
                v.forEach(netDTO -> {
                    Map<String, Object> childMap = new HashMap<>(2);
                    childMap.put("userId", netDTO.getTargetId());
                    childMap.put("userName", netDTO.getTargetName());
                    childMap.put("contactTime", DateUtil.dateTimestamp(netDTO.getContactTime()));
                    childMap.put("contactFrequency", netDTO.getContactFrequency());
                    childMap.put("distance", CalculateUtil.calculateDistance(netDTO.getRssi()));
                    child.add(childMap);
                });
                map.put("dic", child);
                result.add(map);
            });
            Collections.sort(result, (map1, map2) -> {
                String hour1 = map1.get("hour").toString();
                String hour2 = map2.get("hour").toString();
                return hour1.compareTo(hour2);
            });
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[获取个人接触网络-大屏]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取个人接触网络-大屏]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "查询接触人数")
    @PostMapping(value = "/countContactNumber")
    public BaseResponse<List<Map<String, Object>>> countContactNumber(@RequestBody AppDataQueryVO vo) {
        try {

            List<Map<String, Object>> result = ttInfectMacService.countContactNumber(vo.getUserId(), DateUtil.parse(vo.getStartDate()), DateUtil.parse(vo.getEndDate()));
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[查询接触人数]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[查询接触人数]失败 userId:{}, 原因：{}", vo.getUserId(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "获取有接触网络数据的日期")
    @GetMapping(value = "/getContactNetDate")
    public BaseResponse<Map<String, String>> getContactNetDate() {
        try {
            Map<String,String> result=ttContactMapService.getContactNetDate();
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[获取接触网络日期]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取接触网络日期]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "获取接触网络")
    @GetMapping(value = "/getContactNet")
    public BaseResponse<List<TtContactMapVO>> getContactNet(@ApiParam(value = "部门id") @RequestParam(value = "baId", required = false, defaultValue = "") String baId,
                                                            @ApiParam(value = "开始时间") @RequestParam("startTime") String startTime,
                                                            @ApiParam(value = "结束时间") @RequestParam("endTime") String endTime) {
        try {
            if (StringUtils.isEmpty(baId)) {
                baId = null;
            }
            List<TtContactMapVO> result = new ArrayList<>();
            List<TtContactMapDTO> ttContactMapDTOList =
                    Optional.ofNullable(ttContactMapService.getContactNet(baId, DateUtil.parse(startTime), DateUtil.parse(endTime)))
                            .orElse(new ArrayList<>());
            Map<String, List<TtContactMapDTO>> collect = ttContactMapDTOList.stream().collect(Collectors.groupingBy(TtContactMapDTO::getUserId));
            collect.forEach((k, v) -> {
                TtContactMapVO vo = new TtContactMapVO();
                vo.setUserId(k);

                Set<TtContactNodeVO> edit = new HashSet<>();
                v.forEach(dto -> {
                    vo.setUserName(dto.getUserName());
                    vo.setInfected(dto.getInfected());
                    vo.setQuarantine(dto.getQuarantine());
                    TtContactNodeVO nodeVO = new TtContactNodeVO();
                    nodeVO.setSourceId(k);
                    nodeVO.setSourceName(dto.getUserName());
                    nodeVO.setTargetId(dto.getTargetId());
                    nodeVO.setTargetName(dto.getTargetName());
                    nodeVO.setTargetInfected(dto.getTargetInfected());
                    nodeVO.setTargetQuarantine(dto.getTargetQuarantine());
                    edit.add(nodeVO);
                });
                vo.setEdit(edit);
                result.add(vo);
            });
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[获取接触网络]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[获取接触网络]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "密切接触者查询")
    @PostMapping("/listCloseContacts")
    public BaseResponse<TtCloseContactVO> listCloseContacts(@ApiParam(value = "用户姓名") @RequestParam("userName") String userName,
                                                            @ApiParam(value = "开始时间") @RequestParam("startTime") String startTime,
                                                            @ApiParam(value = "结束时间") @RequestParam("endTime") String endTime) {
        try {
            List<TtCloseContactDTO> dtoList = ttInfectMacService.listCloseContacts(userName, DateUtil.parse(startTime), DateUtil.parse(endTime));
            List<TtCloseContactVO> result = dtoList.stream().map(dto -> {
                TtCloseContactVO vo = new TtCloseContactVO();
                BeanUtils.copyProperties(dto, vo);
                vo.setContactTime(DateUtil.dateTimestamp(dto.getContactTime()));
                String distance = CalculateUtil.calculateDistance(dto.getRssi());
                vo.setDistance(distance);
                return vo;
            }).collect(Collectors.toList());
            return new BaseResponse(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[密切接触者查询]失败 userName：{}，原因：{}", userName, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[密切接触者查询]失败 userName：{}，原因：{}", userName, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "密切接触者导出")
    @GetMapping("/exportCloseContacts")
    public BaseResponse<Boolean> exportCloseContacts(@ApiParam(value = "用户姓名", required = true) @RequestParam("userName") String userName,
                                                     @ApiParam(value = "开始时间") @RequestParam("startTime") String startTime,
                                                     @ApiParam(value = "结束时间") @RequestParam("endTime") String endTime,
                                                     HttpServletResponse response) {
        try {
            List<TtCloseContactDTO> dtoList = ttInfectMacService.listCloseContacts(userName, DateUtil.parse(startTime), DateUtil.parse(endTime));
            List<Map<String, Object>> dataList = new ArrayList<>();
            dtoList.forEach(dto -> {
                Map<String, Object> map = new HashMap<>();
                map.put("userName", dto.getUserName());
                map.put("contactTime", DateUtil.dateFormat(dto.getContactTime()));
                map.put("times", dto.getTimes());
                map.put("distance", CalculateUtil.calculateDistance(dto.getRssi()));
                dataList.add(map);
            });
            String fileName = userName + "_密切接触者";
            String[] heads = {"姓名", "接触时间", "接触次数", "接触距离"};
            // 将Map中的key，列出来
            String[] cols = {"userName", "contactTime", "times", "distance"};
            Boolean result = ExcelUtil.exportExcel(response, fileName, heads, cols, dataList);
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[密切接触者导出]失败 userName：{}，原因：{}", userName, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[密切接触者导出]失败 userName：{}，原因：{}", userName, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "用户信息模糊搜索")
    @PostMapping(value = "/listUserByCondition")
    public BaseResponse<List<TtContactMapRespVO>> listUserByCondition(@RequestBody TtContactMapReqVO contactMapReqVO) {
        try {
            Integer quarantine = contactMapReqVO.getQuarantine();
            Integer infected = contactMapReqVO.getInfected();
            if (ObjectUtils.isEmpty(quarantine) || ObjectUtils.isEmpty(infected)) {
                throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
            }
            String baId = StringUtils.isEmpty(contactMapReqVO.getBaId()) ? null : contactMapReqVO.getBaId();
            String userName = StringUtils.isEmpty(contactMapReqVO.getUserName()) ? null : contactMapReqVO.getUserName();
            List<TtContactMapRespDTO> ttContactMapRespDTOList = ttContactMapService.listUserByCondition(baId, userName, quarantine, infected);
            List<TtContactMapRespVO> result = ttContactMapRespDTOList.stream().map(dto -> {
                TtContactMapRespVO vo = new TtContactMapRespVO();
                BeanUtils.copyProperties(dto, vo);
                return vo;
            }).collect(Collectors.toList());
            return new BaseResponse<>(AppCodeInfo.SUCCESS, result);
        } catch (GlobalException e) {
            log.info("[用户信息模糊搜索]失败 userName：{}，原因：{}", contactMapReqVO.getUserName(), e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[用户信息模糊搜索]失败 userName：{}，原因：{}", contactMapReqVO.getUserName(), e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }


    @ApiOperation(value = "添加/重置 感染的人")
    @PostMapping(value = "/updateInfectedByUserId")
    public BaseResponse<Boolean> updateInfectedByUserId(@ApiParam(value = "用户id", required = true) @RequestParam("userId") String userId,
                                                        @ApiParam(value = "感染，0未感染，1感染", required = true) @RequestParam("infected") Integer infected) {
        try {
            Integer result = ttContactMapService.updateInfectedQuarantineByUserId(userId, infected, AppConstant.TT_CONTACT_MAP_INFECTED);
            if (result > 0) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[感染状态修改]失败 userId：{}，原因：{}", userId, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[感染状态修改]失败 userId：{}，原因：{}", userId, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "添加/重置 隔离的人")
    @PostMapping(value = "/updateQuarantineByUserId")
    public BaseResponse<Boolean> updateQuarantineByUserId(@ApiParam(value = "用户id", required = true) @RequestParam("userId") String userId,
                                                          @ApiParam(value = "隔离，0未隔离，1隔离", required = true) @RequestParam("quarantine") Integer quarantine) {
        try {
            Integer result = ttContactMapService.updateInfectedQuarantineByUserId(userId, quarantine, AppConstant.TT_CONTACT_MAP_QUARANTINE);
            if (result > 0) {
                return new BaseResponse<>(AppCodeInfo.SUCCESS, Boolean.TRUE);
            }
            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[隔离状态修改]失败 userId：{}，原因：{}", userId, e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[隔离状态修改]失败 userId：{}，原因：{}", userId, e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    @ApiOperation(value = "调用仿真系统")
    @PostMapping(value = "/callSimulation")
    public BaseResponse<Boolean> callSimulation() {
        try {
            String testData = "{\"responseCode\":0,\"responseMsg\":\"查询成功\",\"result\":[{\"userId\":\"0\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"0\",\"targetId\":\"0\"},{\"sourceId\":\"0\",\"targetId\":\"1\"},{\"sourceId\":\"0\",\"targetId\":\"2\"},{\"sourceId\":\"0\",\"targetId\":\"3\"},{\"sourceId\":\"0\",\"targetId\":\"4\"},{\"sourceId\":\"0\",\"targetId\":\"5\"},{\"sourceId\":\"0\",\"targetId\":\"6\"},{\"sourceId\":\"0\",\"targetId\":\"7\"},{\"sourceId\":\"0\",\"targetId\":\"8\"},{\"sourceId\":\"0\",\"targetId\":\"9\"}]},{\"userId\":\"1\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"1\",\"targetId\":\"0\"},{\"sourceId\":\"1\",\"targetId\":\"1\"},{\"sourceId\":\"1\",\"targetId\":\"2\"},{\"sourceId\":\"1\",\"targetId\":\"3\"},{\"sourceId\":\"1\",\"targetId\":\"4\"},{\"sourceId\":\"1\",\"targetId\":\"5\"},{\"sourceId\":\"1\",\"targetId\":\"6\"},{\"sourceId\":\"1\",\"targetId\":\"7\"},{\"sourceId\":\"1\",\"targetId\":\"8\"},{\"sourceId\":\"1\",\"targetId\":\"9\"}]},{\"userId\":\"2\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"2\",\"targetId\":\"0\"},{\"sourceId\":\"2\",\"targetId\":\"1\"},{\"sourceId\":\"2\",\"targetId\":\"2\"},{\"sourceId\":\"2\",\"targetId\":\"3\"},{\"sourceId\":\"2\",\"targetId\":\"4\"},{\"sourceId\":\"2\",\"targetId\":\"5\"},{\"sourceId\":\"2\",\"targetId\":\"6\"},{\"sourceId\":\"2\",\"targetId\":\"7\"},{\"sourceId\":\"2\",\"targetId\":\"8\"},{\"sourceId\":\"2\",\"targetId\":\"9\"}]},{\"userId\":\"3\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"3\",\"targetId\":\"0\"},{\"sourceId\":\"3\",\"targetId\":\"1\"},{\"sourceId\":\"3\",\"targetId\":\"2\"},{\"sourceId\":\"3\",\"targetId\":\"3\"},{\"sourceId\":\"3\",\"targetId\":\"4\"},{\"sourceId\":\"3\",\"targetId\":\"5\"},{\"sourceId\":\"3\",\"targetId\":\"6\"},{\"sourceId\":\"3\",\"targetId\":\"7\"},{\"sourceId\":\"3\",\"targetId\":\"8\"},{\"sourceId\":\"3\",\"targetId\":\"9\"}]},{\"userId\":\"4\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"4\",\"targetId\":\"0\"},{\"sourceId\":\"4\",\"targetId\":\"1\"},{\"sourceId\":\"4\",\"targetId\":\"2\"},{\"sourceId\":\"4\",\"targetId\":\"3\"},{\"sourceId\":\"4\",\"targetId\":\"4\"},{\"sourceId\":\"4\",\"targetId\":\"5\"},{\"sourceId\":\"4\",\"targetId\":\"6\"},{\"sourceId\":\"4\",\"targetId\":\"7\"},{\"sourceId\":\"4\",\"targetId\":\"8\"},{\"sourceId\":\"4\",\"targetId\":\"9\"}]},{\"userId\":\"5\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"5\",\"targetId\":\"0\"},{\"sourceId\":\"5\",\"targetId\":\"1\"},{\"sourceId\":\"5\",\"targetId\":\"2\"},{\"sourceId\":\"5\",\"targetId\":\"3\"},{\"sourceId\":\"5\",\"targetId\":\"4\"},{\"sourceId\":\"5\",\"targetId\":\"5\"},{\"sourceId\":\"5\",\"targetId\":\"6\"},{\"sourceId\":\"5\",\"targetId\":\"7\"},{\"sourceId\":\"5\",\"targetId\":\"8\"},{\"sourceId\":\"5\",\"targetId\":\"9\"}]},{\"userId\":\"6\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"6\",\"targetId\":\"0\"},{\"sourceId\":\"6\",\"targetId\":\"1\"},{\"sourceId\":\"6\",\"targetId\":\"2\"},{\"sourceId\":\"6\",\"targetId\":\"3\"},{\"sourceId\":\"6\",\"targetId\":\"4\"},{\"sourceId\":\"6\",\"targetId\":\"5\"},{\"sourceId\":\"6\",\"targetId\":\"6\"},{\"sourceId\":\"6\",\"targetId\":\"7\"},{\"sourceId\":\"6\",\"targetId\":\"8\"},{\"sourceId\":\"6\",\"targetId\":\"9\"}]},{\"userId\":\"7\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"7\",\"targetId\":\"0\"},{\"sourceId\":\"7\",\"targetId\":\"1\"},{\"sourceId\":\"7\",\"targetId\":\"2\"},{\"sourceId\":\"7\",\"targetId\":\"3\"},{\"sourceId\":\"7\",\"targetId\":\"4\"},{\"sourceId\":\"7\",\"targetId\":\"5\"},{\"sourceId\":\"7\",\"targetId\":\"6\"},{\"sourceId\":\"7\",\"targetId\":\"7\"},{\"sourceId\":\"7\",\"targetId\":\"8\"},{\"sourceId\":\"7\",\"targetId\":\"9\"}]},{\"userId\":\"8\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"8\",\"targetId\":\"0\"},{\"sourceId\":\"8\",\"targetId\":\"1\"},{\"sourceId\":\"8\",\"targetId\":\"2\"},{\"sourceId\":\"8\",\"targetId\":\"3\"},{\"sourceId\":\"8\",\"targetId\":\"4\"},{\"sourceId\":\"8\",\"targetId\":\"5\"},{\"sourceId\":\"8\",\"targetId\":\"6\"},{\"sourceId\":\"8\",\"targetId\":\"7\"},{\"sourceId\":\"8\",\"targetId\":\"8\"},{\"sourceId\":\"8\",\"targetId\":\"9\"}]},{\"userId\":\"9\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"9\",\"targetId\":\"0\"},{\"sourceId\":\"9\",\"targetId\":\"1\"},{\"sourceId\":\"9\",\"targetId\":\"2\"},{\"sourceId\":\"9\",\"targetId\":\"3\"},{\"sourceId\":\"9\",\"targetId\":\"4\"},{\"sourceId\":\"9\",\"targetId\":\"5\"},{\"sourceId\":\"9\",\"targetId\":\"6\"},{\"sourceId\":\"9\",\"targetId\":\"7\"},{\"sourceId\":\"9\",\"targetId\":\"8\"},{\"sourceId\":\"9\",\"targetId\":\"9\"}]},{\"userId\":\"10\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"10\",\"targetId\":\"0\"},{\"sourceId\":\"10\",\"targetId\":\"1\"},{\"sourceId\":\"10\",\"targetId\":\"2\"},{\"sourceId\":\"10\",\"targetId\":\"3\"},{\"sourceId\":\"10\",\"targetId\":\"4\"},{\"sourceId\":\"10\",\"targetId\":\"5\"},{\"sourceId\":\"10\",\"targetId\":\"6\"},{\"sourceId\":\"10\",\"targetId\":\"7\"},{\"sourceId\":\"10\",\"targetId\":\"8\"},{\"sourceId\":\"10\",\"targetId\":\"9\"}]},{\"userId\":\"11\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"11\",\"targetId\":\"0\"},{\"sourceId\":\"11\",\"targetId\":\"1\"},{\"sourceId\":\"11\",\"targetId\":\"2\"},{\"sourceId\":\"11\",\"targetId\":\"3\"},{\"sourceId\":\"11\",\"targetId\":\"4\"},{\"sourceId\":\"11\",\"targetId\":\"5\"},{\"sourceId\":\"11\",\"targetId\":\"6\"},{\"sourceId\":\"11\",\"targetId\":\"7\"},{\"sourceId\":\"11\",\"targetId\":\"8\"},{\"sourceId\":\"11\",\"targetId\":\"9\"}]},{\"userId\":\"12\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"12\",\"targetId\":\"0\"},{\"sourceId\":\"12\",\"targetId\":\"1\"},{\"sourceId\":\"12\",\"targetId\":\"2\"},{\"sourceId\":\"12\",\"targetId\":\"3\"},{\"sourceId\":\"12\",\"targetId\":\"4\"},{\"sourceId\":\"12\",\"targetId\":\"5\"},{\"sourceId\":\"12\",\"targetId\":\"6\"},{\"sourceId\":\"12\",\"targetId\":\"7\"},{\"sourceId\":\"12\",\"targetId\":\"8\"},{\"sourceId\":\"12\",\"targetId\":\"9\"}]},{\"userId\":\"13\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"13\",\"targetId\":\"0\"},{\"sourceId\":\"13\",\"targetId\":\"1\"},{\"sourceId\":\"13\",\"targetId\":\"2\"},{\"sourceId\":\"13\",\"targetId\":\"3\"},{\"sourceId\":\"13\",\"targetId\":\"4\"},{\"sourceId\":\"13\",\"targetId\":\"5\"},{\"sourceId\":\"13\",\"targetId\":\"6\"},{\"sourceId\":\"13\",\"targetId\":\"7\"},{\"sourceId\":\"13\",\"targetId\":\"8\"},{\"sourceId\":\"13\",\"targetId\":\"9\"}]},{\"userId\":\"14\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"14\",\"targetId\":\"0\"},{\"sourceId\":\"14\",\"targetId\":\"1\"},{\"sourceId\":\"14\",\"targetId\":\"2\"},{\"sourceId\":\"14\",\"targetId\":\"3\"},{\"sourceId\":\"14\",\"targetId\":\"4\"},{\"sourceId\":\"14\",\"targetId\":\"5\"},{\"sourceId\":\"14\",\"targetId\":\"6\"},{\"sourceId\":\"14\",\"targetId\":\"7\"},{\"sourceId\":\"14\",\"targetId\":\"8\"},{\"sourceId\":\"14\",\"targetId\":\"9\"}]},{\"userId\":\"15\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"15\",\"targetId\":\"0\"},{\"sourceId\":\"15\",\"targetId\":\"1\"},{\"sourceId\":\"15\",\"targetId\":\"2\"},{\"sourceId\":\"15\",\"targetId\":\"3\"},{\"sourceId\":\"15\",\"targetId\":\"4\"},{\"sourceId\":\"15\",\"targetId\":\"5\"},{\"sourceId\":\"15\",\"targetId\":\"6\"},{\"sourceId\":\"15\",\"targetId\":\"7\"},{\"sourceId\":\"15\",\"targetId\":\"8\"},{\"sourceId\":\"15\",\"targetId\":\"9\"}]},{\"userId\":\"16\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"16\",\"targetId\":\"0\"},{\"sourceId\":\"16\",\"targetId\":\"1\"},{\"sourceId\":\"16\",\"targetId\":\"2\"},{\"sourceId\":\"16\",\"targetId\":\"3\"},{\"sourceId\":\"16\",\"targetId\":\"4\"},{\"sourceId\":\"16\",\"targetId\":\"5\"},{\"sourceId\":\"16\",\"targetId\":\"6\"},{\"sourceId\":\"16\",\"targetId\":\"7\"},{\"sourceId\":\"16\",\"targetId\":\"8\"},{\"sourceId\":\"16\",\"targetId\":\"9\"}]},{\"userId\":\"17\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"17\",\"targetId\":\"0\"},{\"sourceId\":\"17\",\"targetId\":\"1\"},{\"sourceId\":\"17\",\"targetId\":\"2\"},{\"sourceId\":\"17\",\"targetId\":\"3\"},{\"sourceId\":\"17\",\"targetId\":\"4\"},{\"sourceId\":\"17\",\"targetId\":\"5\"},{\"sourceId\":\"17\",\"targetId\":\"6\"},{\"sourceId\":\"17\",\"targetId\":\"7\"},{\"sourceId\":\"17\",\"targetId\":\"8\"},{\"sourceId\":\"17\",\"targetId\":\"9\"}]},{\"userId\":\"18\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"18\",\"targetId\":\"0\"},{\"sourceId\":\"18\",\"targetId\":\"1\"},{\"sourceId\":\"18\",\"targetId\":\"2\"},{\"sourceId\":\"18\",\"targetId\":\"3\"},{\"sourceId\":\"18\",\"targetId\":\"4\"},{\"sourceId\":\"18\",\"targetId\":\"5\"},{\"sourceId\":\"18\",\"targetId\":\"6\"},{\"sourceId\":\"18\",\"targetId\":\"7\"},{\"sourceId\":\"18\",\"targetId\":\"8\"},{\"sourceId\":\"18\",\"targetId\":\"9\"}]},{\"userId\":\"19\",\"quarantine\":0,\"infected\":1,\"edge\":[{\"sourceId\":\"19\",\"targetId\":\"0\"},{\"sourceId\":\"19\",\"targetId\":\"1\"},{\"sourceId\":\"19\",\"targetId\":\"2\"},{\"sourceId\":\"19\",\"targetId\":\"3\"},{\"sourceId\":\"19\",\"targetId\":\"4\"},{\"sourceId\":\"19\",\"targetId\":\"5\"},{\"sourceId\":\"19\",\"targetId\":\"6\"},{\"sourceId\":\"19\",\"targetId\":\"7\"},{\"sourceId\":\"19\",\"targetId\":\"8\"},{\"sourceId\":\"19\",\"targetId\":\"9\"}]}]}";

            Pointer pointerResult = new Memory(10000);
            JNAConfig.INSTANCE.process(1, 14, pointerResult, testData);
            String respData = pointerResult.getString(0);
            log.info("result={}", respData);

            // 释放 NativeHeap空间
            Native.free(Pointer.nativeValue(pointerResult));
            Pointer.nativeValue(pointerResult, 0);

            return new BaseResponse<>(AppCodeInfo.FAILED, Boolean.FALSE);
        } catch (GlobalException e) {
            log.info("[调用仿真系统]失败 原因：{}", e.getMsg());
            return new BaseResponse(e.getCode(), e.getMsg());
        } catch (Exception e) {
            log.info("[调用仿真系统]失败 原因：{}", e.getMessage());
            return new BaseResponse(AppCodeInfo.SYS_ERROR, e.getMessage());
        }
    }

    /**
     * TtContactMapNet 数据对象 转 视图对象
     *
     * @param dto
     * @return
     */
    private TtContactMapNetVO ttContactMapNetDTO2VO(TtContactMapNetDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        TtContactMapNetVO netVO = new TtContactMapNetVO();
        netVO.setUserId(dto.getTargetId());
        netVO.setUserName(dto.getTargetName());
        netVO.setContactTime(DateUtil.dateTimestamp(dto.getContactTime()));
        netVO.setContactFrequency(dto.getContactFrequency());
        netVO.setDistance(CalculateUtil.calculateDistance(dto.getRssi()));
        return netVO;
    }

}
