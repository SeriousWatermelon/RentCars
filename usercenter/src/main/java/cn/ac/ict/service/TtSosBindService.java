package cn.ac.ict.service;

import cn.ac.ict.entity.TtSosBindEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TtSosBindService extends IService<TtSosBindEntity> {

    /**
     * 用户绑定手环
     *
     * @param ttSosBindEntity
     * @return
     */
    Boolean userMacBind(TtSosBindEntity ttSosBindEntity);

    /**
     * 用户解绑手环
     *
     * @param reqSosBindEntity
     * @return
     */
    Boolean userMacUnBind(TtSosBindEntity reqSosBindEntity);


    /**
     * 查询指定用户的 手环绑定信息
     *
     * @param userId
     * @return
     */
    TtSosBindEntity getUserMacByUserId(String userId);

    /**
     * 查询指定 手环的 用户绑定信息
     *
     * @param mac
     * @return
     */
    TtSosBindEntity getUserMacByMac(String mac);

    /**
     * 查询所有被绑定的mac地址
     *
     * @return
     */
    List<String> getMacBind();

}
