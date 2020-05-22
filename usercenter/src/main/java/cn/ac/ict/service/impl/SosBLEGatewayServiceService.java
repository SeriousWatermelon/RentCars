package cn.ac.ict.service.impl;

import cn.ac.ict.config.ScreenWebsocketServer;
import cn.ac.ict.config.SpringBeanConfig;
import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.entity.*;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.service.*;
import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.utils.RawDataDecodeUtil;
import cn.ac.ict.vo.GatewayDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class SosBLEGatewayServiceService implements SosBLEGatewayService {

    @Resource
    private TtWristbandDataService ttWristbandDataService;

    @Resource
    private TtRawDataService ttRawDataService;

    @Resource
    private TtSosBindService ttSosBindService;

    @Resource
    private TtInfectMacService ttInfectMacService;

    @Resource
    private TtHourMacService ttHourMacService;

    @Resource
    private TtContactMapService ttContactMapService;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void bleDataUpload(List<GatewayDataVO> gatewayDataVOList) {
        Map<String, Object> map = gateWayDataVOList2EntityList(gatewayDataVOList);

        // 保存 原数据
        List<TtWristbandDataEntity> ttWristbandDataEntityList = (List<TtWristbandDataEntity>) map.get("ttWristbandDataEntityList");
        // 保存 raw解析出来的用户步数、体温、里程、卡路里等数据
        List<TtRawDataEntity> ttRawDataEntityList = (List<TtRawDataEntity>) map.get("ttRawDataEntityList");
        if (ttWristbandDataEntityList.size() > 0) {
            ttWristbandDataService.saveBatch(ttWristbandDataEntityList);
            ttRawDataService.saveBatch(ttRawDataEntityList);
            // 保存到 tt_hour_mac
            saveTtHourMacAndTtInfectMac(gatewayDataVOList);
        }
    }

    /**
     * 网关 上传的 原数据 封装成 tt_hour_mac，并保存.
     *
     * @param gatewayDataVOList
     * @return
     */
    private void saveTtHourMacAndTtInfectMac(List<GatewayDataVO> gatewayDataVOList) {
        List<TtHourMacEntity> ttHourMacEntityList = new ArrayList<>();
        List<TtInfectMacEntity> ttInfectMacEntityList = new ArrayList<>();
        for (int i = 0; i < gatewayDataVOList.size(); i++) {
            GatewayDataVO gatewayDataVO = gatewayDataVOList.get(i);
            // 获取用户ID，并排除无效mac
            TtSosBindEntity ttSosBindEntity = ttSosBindService.getUserMacByMac(gatewayDataVO.getMac());
            if (ObjectUtils.isEmpty(ttSosBindEntity)) {
                continue;
            }
            for (int j = 0; j < gatewayDataVOList.size(); j++) {
                // 排除 用户自己
                if (i != j) {
                    TtHourMacEntity ttHourMacEntity = new TtHourMacEntity();
                    ttHourMacEntity.setUserId(ttSosBindEntity.getUserId());
                    // 接触次数，每条数据表示一个人，因此每条数据之间的接触次数都是1
                    ttHourMacEntity.setContactNumber(1);
                    ttHourMacEntity.setContactTime(gatewayDataVOList.get(j).getTimestamp());
                    // 用户接触人的mac地址
                    ttHourMacEntity.setMac(gatewayDataVOList.get(j).getMac());
                    // 用户接触人的信号强度
                    ttHourMacEntity.setRssi(gatewayDataVOList.get(j).getRssi().toString());
                    ttHourMacEntityList.add(ttHourMacEntity);
                    // 封装 tt_infect_mac
                    TtInfectMacEntity infectMacEntity = new TtInfectMacEntity();
                    infectMacEntity.setUserId(ttSosBindEntity.getUserId());
                    infectMacEntity.setCreateTime(gatewayDataVOList.get(j).getTimestamp());
                    infectMacEntity.setMac(gatewayDataVOList.get(j).getMac());
                    infectMacEntity.setRssi(gatewayDataVOList.get(j).getRssi().toString());
                    ttInfectMacEntityList.add(infectMacEntity);
                }
            }
        }
        ttInfectMacService.saveBatch(ttInfectMacEntityList);
        Boolean saveResult = ttHourMacService.saveBatch(ttHourMacEntityList);

        // tt_hour_mac保存成功后，才保存 tt_contact_map
        if (saveResult) {
            // 提取 接触时间，去重，用于统计接触网络
            List<String> dateList = new ArrayList<>();
            for (TtHourMacEntity ttHourMacEntity : ttHourMacEntityList) {
                Date contactTime = ttHourMacEntity.getContactTime();
                String contactTimeStr = DateUtil.dateFormat(contactTime);
                dateList.add(contactTimeStr);
            }
            saveTtContactMap(dateList);
        }
    }

    private void saveTtContactMap(List<String> dateList) {
        for (String date : dateList) {
            List<TtContactMapEntity> ttContactMapEntityList = new ArrayList<>();
            List<Map<String, Object>> mapList = ttHourMacService.countByContactTime(DateUtil.parse(date));
            log.info("mapList.size=", mapList.size() + "");
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
            ttContactMapService.deleteByContactTime(DateUtil.parse(date));
            ttContactMapService.saveBatch(ttContactMapEntityList);
        }
        // WebSocket通知前端
        ScreenWebsocketServer websocketServer = (ScreenWebsocketServer) SpringBeanConfig.getBean(ScreenWebsocketServer.class);
        websocketServer.sendAll(String.valueOf(AppCodeInfo.SYS_WEB_SOCKET.getCode()));
    }


    /**
     * 网关 上传的 原数据 封装成 Entity
     *
     * @param gatewayDataVOList
     * @return
     */
    private Map<String, Object> gateWayDataVOList2EntityList(List<GatewayDataVO> gatewayDataVOList) {
        // 先拿到网关的数据,并筛选出来
        GatewayDataVO bleDataVO = null;
        Iterator<GatewayDataVO> it = gatewayDataVOList.iterator();
        while (it.hasNext()) {
            GatewayDataVO gatewayDataVO = it.next();
            if (AppConstant.BLE_GATEWAY_TYPE.equals(gatewayDataVO.getType())) {
                bleDataVO = gatewayDataVO;
                it.remove();
            }
        }
        // 数据封装
        Map<String, Object> map = new HashMap<>();
        List<TtWristbandDataEntity> ttWristbandDataEntityList = new ArrayList<>();
        List<TtRawDataEntity> ttRawDataEntityList = new ArrayList<>();
        for (GatewayDataVO vo : gatewayDataVOList) {
            TtWristbandDataEntity entity = new TtWristbandDataEntity();
            BeanUtils.copyProperties(vo, entity);
            entity.setGatewayMac(bleDataVO.getMac());
            entity.setBleTime(vo.getTimestamp());
            ttWristbandDataEntityList.add(entity);
            // 封装rawData的解析数据
            TtRawDataEntity ttRawDataEntity = RawDataDecodeUtil.decodeRawData(entity.getMac(), entity.getRawData());
            ttRawDataEntityList.add(ttRawDataEntity);
        }
        map.put("ttWristbandDataEntityList", ttWristbandDataEntityList);
        map.put("ttRawDataEntityList", ttRawDataEntityList);
        return map;
    }
}
