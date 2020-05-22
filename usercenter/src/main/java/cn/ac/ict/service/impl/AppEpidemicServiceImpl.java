package cn.ac.ict.service.impl;

import cn.ac.ict.config.ScreenWebsocketServer;
import cn.ac.ict.config.SpringBeanConfig;
import cn.ac.ict.entity.TtContactMapEntity;
import cn.ac.ict.entity.TtHourMacEntity;
import cn.ac.ict.entity.TtInfectDataEntity;
import cn.ac.ict.entity.TtInfectMacEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.service.*;
import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.vo.BraceletInfoVO;
import cn.ac.ict.vo.TtInfectDataVO;
import cn.ac.ict.vo.TtInfectMacRespVO;
import cn.ac.ict.vo.TtInfectMacVO;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AppEpidemicServiceImpl implements AppEpidemicService {

    @Resource
    private TtSosBindService ttSosBindService;

    @Resource
    private TtInfectMacService ttInfectMacService;

    @Resource
    private TtInfectDataService ttInfectDataService;

    @Resource
    private TtHourMacService ttHourMacService;

    @Resource
    private TtContactMapService ttContactMapService;


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Override
    public List<TtInfectMacRespVO> macInfoPost(BraceletInfoVO braceletInfo) {
        // 筛选无效的mac地址
        List<String> usefulMac = ttSosBindService.getMacBind();
        Map<String, Object> map = checkUsefulMac(braceletInfo, usefulMac);
        // 返回结果 集合
        List<TtInfectMacRespVO> respVOS = (List<TtInfectMacRespVO>) map.get("respVOS");
        // 要保存的 有效 结果集
        List<TtInfectMacEntity> ttInfectMacEntityList = (List<TtInfectMacEntity>) map.get("ttInfectMacEntityList");
        TtInfectDataEntity ttInfectDataEntity = (TtInfectDataEntity) map.get("ttInfectDataEntity");
        if (ttInfectMacEntityList.size() > 0) {
            // 保存 用户接触的mac信息
            ttInfectMacService.saveBatch(ttInfectMacEntityList);
            // 保存 用户上传信息的位置
            ttInfectDataService.save(ttInfectDataEntity);

            // 同步 tt_hour_mac表 与 tt_contact_map表
            saveTtHourMac(ttInfectMacEntityList);
        }
        return respVOS;
    }

    /**
     * 保存 TtHourMac 和 TtContactMap数据
     */
    private void saveTtHourMac(List<TtInfectMacEntity> ttInfectMacEntityList) {
        ttInfectMacEntityList.forEach(entity -> {
            List<TtHourMacEntity> ttHourMacEntityList = Lists.newArrayList();
            List<Map<String, Object>> mapList = ttInfectMacService.countByMacGroupHours(entity.getCreateTime());
            mapList.forEach(map -> {
                TtHourMacEntity ttHourMacEntity = new TtHourMacEntity();
                ttHourMacEntity.setUserId((String) map.get("userId"));
                String contactNumber = map.get("countMacs").toString();
                ttHourMacEntity.setContactNumber(Integer.parseInt(contactNumber));
                ttHourMacEntity.setMac((String) map.get("mac"));
                ttHourMacEntity.setRssi((String) map.get("rssi"));
                ttHourMacEntity.setContactTime((Date) map.get("hours"));
                ttHourMacEntity.setCreateTime(DateUtil.nowDate());
                ttHourMacEntityList.add(ttHourMacEntity);
            });
            ttHourMacService.deleteByContactTime(entity.getCreateTime());
            ttHourMacService.saveBatch(ttHourMacEntityList);
        });
        saveTtContactMap(ttInfectMacEntityList);
    }

    /**
     * 保存 TtHourMac 和 TtContactMap数据
     */
    private void saveTtContactMap(List<TtInfectMacEntity> ttInfectMacEntityList) {
        for (TtInfectMacEntity entity : ttInfectMacEntityList) {
            List<TtContactMapEntity> ttContactMapEntityList = new ArrayList<>();
            List<Map<String, Object>> mapList = ttHourMacService.countByContactTime(entity.getCreateTime());
            mapList.forEach(map -> {
                TtContactMapEntity ttContactMapEntity = new TtContactMapEntity();
                ttContactMapEntity.setInfected(0);
                ttContactMapEntity.setQuarantine(0);
                ttContactMapEntity.setMac((String) map.get("mac"));
                ttContactMapEntity.setSourceId((String) map.get("userId"));
                ttContactMapEntity.setTargetId((String) map.get("targetId"));
                ttContactMapEntity.setUserId((String) map.get("userId"));
                ttContactMapEntity.setBaId((String) map.get("baId"));
                ttContactMapEntity.setContactTime((Date) map.get("contactTime"));
                ttContactMapEntityList.add(ttContactMapEntity);
            });
            ttContactMapService.deleteByContactTime(entity.getCreateTime());
            ttContactMapService.saveBatch(ttContactMapEntityList);
            // WebSocket通知前端
            ScreenWebsocketServer websocketServer = (ScreenWebsocketServer) SpringBeanConfig.getBean(ScreenWebsocketServer.class);
            websocketServer.sendAll(String.valueOf(AppCodeInfo.SYS_WEB_SOCKET.getCode()));
        }
    }

    /**
     * 上传mac地址，类型转换
     *
     * @param braceletInfo
     * @param useFulMacList
     * @return
     */
    private Map<String, Object> checkUsefulMac(BraceletInfoVO braceletInfo, List<String> useFulMacList) {
        // 数据格式转换，并筛选有效数据
        String userId = braceletInfo.getUserId();
        TtInfectDataVO ttInfectDataVO = braceletInfo.getLocation();
        List<TtInfectMacVO> ttInfectMacVOList = braceletInfo.getData();

        // 结果集 容器
        Map<String, Object> result = new HashMap<>(4);
        List<TtInfectMacRespVO> respVOS = new ArrayList<>();
        List<TtInfectMacEntity> ttInfectMacEntityList = new ArrayList<>();

        // 筛选有效的mac信息，并封装 respVOS、ttInfectMacEntityList、ttInfectDataEntity 数据集合对象
        ttInfectMacVOList.forEach(request -> {
            String macAddress = request.getMacAddress().toUpperCase();
            TtInfectMacRespVO respVO = new TtInfectMacRespVO();
            if (useFulMacList.contains(macAddress)) {
                TtInfectMacEntity ttInfectMacEntity = ttInfectMacVO2Entity(userId, macAddress, request);
                ttInfectMacEntityList.add(ttInfectMacEntity);

                TtInfectDataEntity ttInfectDataEntity = ttInfectDataVO2Entity(userId, ttInfectDataVO);
                result.put("ttInfectDataEntity", ttInfectDataEntity);

                BeanUtils.copyProperties(ttInfectMacEntity, respVO);
                respVO.setCreateTime(DateUtil.parse(request.getTime()).getTime());
                respVO.setStatus(0);
            } else {
                respVO.setUserId(userId);
                respVO.setMac(macAddress);
                respVO.setRssi(StringUtils.isEmpty(request.getRssi()) ? "-64" : request.getRssi());
                respVO.setCreateTime(DateUtil.parse(request.getTime()).getTime());
                respVO.setStatus(-1);
            }
            respVOS.add(respVO);
        });
        result.put("respVOS", respVOS);
        result.put("ttInfectMacEntityList", ttInfectMacEntityList);
        return result;
    }


    /**
     * TtInfectMacVO 2 Entity
     *
     * @param userId
     * @param macAddress
     * @param request
     * @return
     */
    private TtInfectMacEntity ttInfectMacVO2Entity(String userId, String macAddress, TtInfectMacVO request) {
        TtInfectMacEntity ttInfectMacEntity = new TtInfectMacEntity();
        ttInfectMacEntity.setUserId(userId);
        ttInfectMacEntity.setMac(macAddress);
        ttInfectMacEntity.setRssi(StringUtils.isEmpty(request.getRssi()) ? "-64" : request.getRssi());
        ttInfectMacEntity.setCreateTime(DateUtil.parse(request.getTime()));
        return ttInfectMacEntity;
    }


    /**
     * TtInfectDataVO 2 Entity
     *
     * @param userId
     * @param ttInfectDataVO
     * @return
     */
    private TtInfectDataEntity ttInfectDataVO2Entity(String userId, TtInfectDataVO ttInfectDataVO) {
        TtInfectDataEntity ttInfectDataEntity = new TtInfectDataEntity();
        ttInfectDataEntity.setUserId(userId);
        ttInfectDataEntity.setCity(ttInfectDataVO.getCity());
        ttInfectDataEntity.setCountry(ttInfectDataVO.getCountry());
        ttInfectDataEntity.setProvince(ttInfectDataVO.getProvince());
        ttInfectDataEntity.setLatitude(String.valueOf(ttInfectDataVO.getLat()));
        ttInfectDataEntity.setLongitude(String.valueOf(ttInfectDataVO.getLon()));
        ttInfectDataEntity.setCreateTime(DateUtil.nowDate());
        return ttInfectDataEntity;
    }

}
