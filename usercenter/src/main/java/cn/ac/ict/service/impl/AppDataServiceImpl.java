package cn.ac.ict.service.impl;

import cn.ac.ict.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AppDataServiceImpl implements AppDataService {

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


    @Transactional
    @Override
    public Boolean clearHistory(String userId) {
        ttSosHeartRateService.deleteByUserId(userId);
        ttSosOxygenService.deleteByUserId(userId);
        ttSosPressureService.deleteByUserId(userId);
        ttSosSleepService.deleteByUserId(userId);
        ttSosSportsService.deleteByUserId(userId);
        ttSosTrainService.deleteByUserId(userId);
        return Boolean.TRUE;
    }
}
