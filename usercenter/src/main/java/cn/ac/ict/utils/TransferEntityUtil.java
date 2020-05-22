package cn.ac.ict.utils;

import cn.ac.ict.entity.*;
import cn.ac.ict.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类 <——> 视图对象
 *
 * @author cuiweiman
 * @date 2020/5/7 17:39
 */
public class TransferEntityUtil {

    /**
     * 步行运行实体类 视图对象 ——> 数据库对象
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosSportsEntity ttSosSportsVO2Entity(String userId, TtSosSportsVO vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        TtSosSportsEntity entity = new TtSosSportsEntity();
        entity.setUserId(userId);
        entity.setSteps(Integer.valueOf(vo.getWalkCounts()));
        BigDecimal heat = new BigDecimal(vo.getCalorie());
        entity.setHeat(heat);
        BigDecimal distance = new BigDecimal(vo.getDistance());
        entity.setDistance(distance);
        entity.setTimeConsuming(vo.getTimeConsuming());
        entity.setSportTime(vo.getStartTime());
        entity.setGoalWalk(Integer.valueOf(vo.getGoalWalk()));
        entity.setDetailJson(vo.getDetailJson());
        return entity;
    }


    /**
     * 步行运行实体类 数据库对象 ——> 视图对象
     *
     * @param entity
     * @return
     */
    public static TtSosSportsVO ttSosSportsEntity2VO(TtSosSportsEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosSportsVO vo = new TtSosSportsVO();
        vo.setWalkCounts(entity.getSteps().toString());
        vo.setGoalWalk(entity.getGoalWalk().toString());
        vo.setCalorie(entity.getHeat().toString());
        vo.setTimeConsuming(entity.getTimeConsuming());
        vo.setDistance(entity.getDistance().toString());
        vo.setStartTime(entity.getSportTime());
        vo.setDetailJson(entity.getDetailJson());
        return vo;
    }

    /**
     * 睡眠信息 视图对象 ——> 数据库对象
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosSleepEntity ttSosSleepVO2Entity(String userId, TtSosSleepVO vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        TtSosSleepEntity entity = new TtSosSleepEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setUserId(userId);
        Date startTime = DateUtil.parse(vo.getStartTime());
        entity.setStartTime(startTime);
        Date endTime = DateUtil.parse(vo.getEndTime());
        entity.setEndTime(endTime);
        return entity;
    }

    /**
     * 睡眠信息 数据库对象 ——> 视图对象
     *
     * @param entity
     * @return
     */
    public static TtSosSleepVO ttSosSleepEntity2VO(TtSosSleepEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosSleepVO vo = new TtSosSleepVO();
        BeanUtils.copyProperties(entity, vo);
        String startTime = DateUtil.dateFormat(entity.getStartTime());
        vo.setStartTime(startTime);
        String endTime = DateUtil.dateFormat(entity.getEndTime());
        vo.setEndTime(endTime);
        return vo;
    }

    /**
     * 训练信息 视图对象 ——> 数据库对象
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosTrainEntity ttSosTrainVO2Entity(String userId, TtSosTrainVO vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        TtSosTrainEntity entity = new TtSosTrainEntity();
        entity.setUserId(userId);
        BeanUtils.copyProperties(vo, entity);
        Date startTime = DateUtil.parse(vo.getStartTime());
        entity.setStartTime(startTime);

        Date endTime = DateUtil.parse(vo.getEndTime());
        entity.setEndTime(endTime);

        entity.setTimeConsuming(Integer.valueOf(vo.getTimeConsuming()));
        entity.setFasterRate(Integer.valueOf(vo.getFasterRate()));

        return entity;
    }

    /**
     * 训练信息 数据库对象 ——> 视图对象
     *
     * @param entity
     * @return
     */
    public static TtSosTrainVO ttSosTrainEntity2Vo(TtSosTrainEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosTrainVO vo = new TtSosTrainVO();
        BeanUtils.copyProperties(entity, vo);
        vo.setStartTime(DateUtil.dateFormat(entity.getStartTime()));
        vo.setEndTime(DateUtil.dateFormat(entity.getEndTime()));
        vo.setTimeConsuming(entity.getTimeConsuming().toString());
        vo.setFasterRate(entity.getFasterRate().toString());
        return vo;
    }

    /**
     * 心率信息 视图对象 ——> 数据库对象
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosHeartRateEntity ttSosHeartRateVO2Entity(String userId, TtSosHeartRateVO vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        TtSosHeartRateEntity entity = new TtSosHeartRateEntity();
        entity.setUserId(userId);
        entity.setBmp(vo.getBmp());

        Date createTime = DateUtil.parse(vo.getCreateTime());
        entity.setCreateTime(createTime);
        return entity;
    }


    /**
     * 心率信息 数据库对象 ——> 视图对象
     *
     * @param entity
     * @return
     */
    public static TtSosHeartRateVO ttSosHeartRateEntity2VO(TtSosHeartRateEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosHeartRateVO vo = new TtSosHeartRateVO();
        vo.setBmp(entity.getBmp());
        String createTime = DateUtil.dateFormat(entity.getCreateTime());
        vo.setCreateTime(createTime);
        return vo;
    }

    /**
     * 血压信息 视图对象 ——> 数据库对象
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosPressureEntity ttSosPressVO2Entity(String userId, TtSosPressureVO vo) {
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        TtSosPressureEntity entity = new TtSosPressureEntity();
        entity.setUserId(userId);
        entity.setSpb(vo.getSbp());
        entity.setDpb(vo.getDbp());

        Date createTime = DateUtil.parse(vo.getDateTime());
        entity.setCreateTime(createTime);
        return entity;
    }


    /**
     * 血压信息 数据库对象 ——> 视图对象
     *
     * @param entity
     * @return
     */
    public static TtSosPressureVO ttSosPressureEntity2VO(TtSosPressureEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosPressureVO vo = new TtSosPressureVO();
        vo.setSbp(entity.getSpb());
        vo.setDbp(entity.getDpb());
        String dateTime = DateUtil.dateFormat(entity.getCreateTime());
        vo.setDateTime(dateTime);
        return vo;
    }

    /**
     * 血氧 VO 转 entity
     *
     * @param userId
     * @param vo
     * @return
     */
    public static TtSosOxygenEntity ttSosOxygenVO2Entity(String userId, TtSosOxygenVO vo) {
        TtSosOxygenEntity entity = new TtSosOxygenEntity();
        if (ObjectUtils.isEmpty(vo)) {
            return null;
        }
        entity.setUserId(userId);
        entity.setBloodOxygen(vo.getBloodOxygen());

        Date dateTime = DateUtil.parse(vo.getDateTime());
        entity.setDateTime(dateTime);
        return entity;
    }

    /**
     * 血氧 entity转VO
     *
     * @param entity
     * @return
     */
    public static TtSosOxygenVO ttSosOxygenEntity2VO(TtSosOxygenEntity entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return null;
        }
        TtSosOxygenVO vo = new TtSosOxygenVO();
        vo.setBloodOxygen(entity.getBloodOxygen());
        String dateTime = DateUtil.dateFormat(entity.getDateTime());
        vo.setDateTime(dateTime);
        return vo;
    }

}
