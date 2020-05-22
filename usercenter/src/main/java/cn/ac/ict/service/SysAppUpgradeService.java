package cn.ac.ict.service;

import cn.ac.ict.entity.SysAppUpgradeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysAppUpgradeService extends IService<SysAppUpgradeEntity> {

    /**
     * android、ios 客户端升级
     *
     * @param sysAppUpgrade
     * @param type
     * @return
     */
    Integer upgrade(SysAppUpgradeEntity sysAppUpgrade, Integer type);


    /**
     * 获取升级列表
     *
     * @param type
     * @return
     */
    List<SysAppUpgradeEntity> listSysAppUpgrade(Integer type);
}
