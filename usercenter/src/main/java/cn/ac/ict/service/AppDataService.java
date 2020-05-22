package cn.ac.ict.service;

public interface AppDataService {

    /**
     * 清空指定用户的历史数据：走路、训练、血压、血氧、睡眠、训练
     *
     * @param userId
     * @return
     */
    Boolean clearHistory(String userId);

}
