package cn.ac.ict.service.impl;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.entity.TtSosBindEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.mapper.TtSosBindMapper;
import cn.ac.ict.service.TtSosBindService;
import cn.ac.ict.utils.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TtSosBindServiceImpl extends ServiceImpl<TtSosBindMapper, TtSosBindEntity> implements TtSosBindService {

    @Resource
    private TtSosBindMapper ttSosBindMapper;

    @Override
    public Boolean userMacBind(TtSosBindEntity ttSosBindEntity) {
        //查询改用户是否绑定mac地址
        TtSosBindEntity sosBindUser = getUserMacByUserId(ttSosBindEntity.getUserId());
        if (!ObjectUtils.isEmpty(sosBindUser)) {
            throw new GlobalException(AppCodeInfo.USER_BIND);
        }
        // 校验 mac地址是否被绑定
        TtSosBindEntity sosBindMac = getUserMacByMac(ttSosBindEntity.getMac());
        if (!ObjectUtils.isEmpty(sosBindMac)) {
            throw new GlobalException(AppCodeInfo.MAC_BIND);
        }

        ttSosBindEntity.setBindTime(DateUtil.nowDate());
        ttSosBindEntity.setCreateTime(DateUtil.nowDate());
        ttSosBindEntity.setBindStatus(AppConstant.USER_MAC_BIND);

        Integer result = ttSosBindMapper.insert(ttSosBindEntity);
        return result == 1;
    }

    @Override
    public Boolean userMacUnBind(TtSosBindEntity reqSosBindEntity) {
        TtSosBindEntity ttSosBindEntity = getUserMacByUserId(reqSosBindEntity.getUserId());

        if (ObjectUtils.isEmpty(ttSosBindEntity)) {
            throw new GlobalException(AppCodeInfo.USER_NOT_BIND);
        }
        if (!reqSosBindEntity.getMac().equals(ttSosBindEntity.getMac())) {
            throw new GlobalException(AppCodeInfo.USER_MAC_NOT_BIND);
        }
        ttSosBindEntity.setUnbindTime(DateUtil.nowDate());
        ttSosBindEntity.setBindStatus(AppConstant.USER_MAC_UN_BIND);

        Integer result = ttSosBindMapper.updateById(ttSosBindEntity);
        return result == 1;
    }

    @Override
    public TtSosBindEntity getUserMacByUserId(String userId) {
        LambdaQueryWrapper<TtSosBindEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosBindEntity::getUserId, userId)
                .eq(TtSosBindEntity::getBindStatus, AppConstant.USER_MAC_BIND);
        TtSosBindEntity result = ttSosBindMapper.selectOne(query);
        return result;
    }

    @Override
    public TtSosBindEntity getUserMacByMac(String mac) {
        LambdaQueryWrapper<TtSosBindEntity> query = Wrappers.lambdaQuery();
        query.eq(TtSosBindEntity::getMac, mac)
                .eq(TtSosBindEntity::getBindStatus, AppConstant.USER_MAC_BIND);
        TtSosBindEntity result = ttSosBindMapper.selectOne(query);
        return result;
    }

    @Override
    public List<String> getMacBind() {
        return ttSosBindMapper.getMacBind(AppConstant.USER_MAC_BIND);
    }
}
