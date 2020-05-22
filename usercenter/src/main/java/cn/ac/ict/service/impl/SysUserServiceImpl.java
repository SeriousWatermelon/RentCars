package cn.ac.ict.service.impl;

import cn.ac.ict.constant.AppConstant;
import cn.ac.ict.dto.SysUserDTO;
import cn.ac.ict.entity.SysUserEntity;
import cn.ac.ict.enums.AppCodeInfo;
import cn.ac.ict.exception.GlobalException;
import cn.ac.ict.mapper.SysUserMapper;
import cn.ac.ict.service.SysUserRoleService;
import cn.ac.ict.service.SysUserService;
import cn.ac.ict.utils.DateUtil;
import cn.ac.ict.utils.ShiroUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUserDTO appLogin(String phone, String password) {
        // 校验登录参数
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
        }
        // 校验 账号是否存在
        SysUserDTO sysUserDTO = sysUserMapper.getUserByPhone(phone);
        if (ObjectUtils.isEmpty(sysUserDTO)) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_NOT_EXISTS);
        }
        // 校验密码是否合法
        if (!sysUserDTO.getPassWord().equals(ShiroUtil.EncodeSalt(password, sysUserDTO.getSalt()))) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_PASS_ERROR);
        }
        // 校验账号是否被禁用
        if (AppConstant.ACCOUNT_STATUS_FORBIDDEN.equals(sysUserDTO.getStatus())) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_FORBIDDEN);
        }
        userInfoFill(sysUserDTO);
        return sysUserDTO;
    }

    @Override
    public Boolean appRegister(String phone, String password) {
        // 参数校验
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new GlobalException(AppCodeInfo.PARAM_NOT_EMPTY);
        }
        // 校验 是否已注册
        SysUserDTO sysUserDTO = sysUserMapper.getUserByPhone(phone);
        if (!ObjectUtils.isEmpty(sysUserDTO)) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_REPEATED);
        }
        // 填充 用户信息，并保存
        SysUserEntity entity = new SysUserEntity();
        entity.setLoginName(phone);
        entity.setUserName(phone);
        entity.setCreateTime(DateUtil.nowDate());
        entity.setUpdateTime(DateUtil.nowDate());

        String salt = RandomStringUtils.randomAlphanumeric(20);
        entity.setPassWord(ShiroUtil.EncodeSalt(password, salt));
        entity.setSalt(salt);

        entity.setBapid(AppConstant.ACCOUNT_DEFAULT_BAP_ID);
        entity.setBaid(AppConstant.ACCOUNT_DEFAULT_BA_ID);
        entity.setStatus(AppConstant.ACCOUNT_STATUS_ORDINARY);
        entity.setPhone(phone);
        Integer result = sysUserMapper.insert(entity);
        return result == 1;
    }

    @Override
    public SysUserDTO getUserByUserId(String userId) {
        SysUserDTO result = sysUserMapper.getUserByUserId(userId);
        userInfoFill(result);
        return result;
    }

    @Override
    public SysUserEntity getUserByLoginName(String loginName) {
        LambdaQueryWrapper<SysUserEntity> query = Wrappers.lambdaQuery();
        query.eq(SysUserEntity::getLoginName, loginName);
        SysUserEntity result = sysUserMapper.selectOne(query);
        return result;
    }

    @Override
    public List<SysUserDTO> listUserByBaIdAndUserName(String baId, String userName) {
        List<SysUserDTO> result = sysUserMapper.listUserByBaIdAndUserName(baId, userName);
        result.forEach(dto -> userInfoFill(dto));
        return result;
    }

    @Override
    public Boolean updatePassword(String phone, String password) {
        // 校验 手机号码 是否已注册
        LambdaQueryWrapper<SysUserEntity> queryCheck = Wrappers.lambdaQuery();
        queryCheck.eq(SysUserEntity::getPhone, phone);
        SysUserEntity sysUserEntity = sysUserMapper.selectOne(queryCheck);
        if (ObjectUtils.isEmpty(sysUserEntity)) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_NOT_EXISTS);
        }

        String salt = RandomStringUtils.randomAlphanumeric(20);
        String newPassword = ShiroUtil.EncodeSalt(password, salt);

        sysUserEntity.setSalt(salt);
        sysUserEntity.setPassWord(newPassword);

        Integer result = sysUserMapper.updateById(sysUserEntity);
        return result == 1;
    }

    @Override
    public Boolean updateUser(SysUserEntity sysUserEntity) {
        // 校验 用户是否存在
        SysUserEntity user = sysUserMapper.selectById(sysUserEntity.getId());
        if (ObjectUtils.isEmpty(user)) {
            throw new GlobalException(AppCodeInfo.ACCOUNT_NOT_EXISTS);
        }
        user.setUpdateTime(DateUtil.nowDate());
        // 修改/完善 用户信息
        if (!StringUtils.isEmpty(sysUserEntity.getUserName())) {
            user.setUserName(sysUserEntity.getUserName());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getLoginName())) {
            user.setLoginName(sysUserEntity.getLoginName());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getBapid())) {
            user.setBapid(sysUserEntity.getBapid());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getBaid())) {
            user.setBaid(sysUserEntity.getBaid());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getGender())) {
            user.setGender(sysUserEntity.getGender());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getAge())) {
            user.setAge(sysUserEntity.getAge());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getStature())) {
            user.setStature(sysUserEntity.getStature());
        }
        if (!StringUtils.isEmpty(sysUserEntity.getWeight())) {
            user.setWeight(sysUserEntity.getWeight());
        }
        if (!ObjectUtils.isEmpty(sysUserEntity.getTargetSteps())) {
            user.setTargetSteps(sysUserEntity.getTargetSteps());
        }
        if (!ObjectUtils.isEmpty(sysUserEntity.getStepsDistance())) {
            user.setStepsDistance(sysUserEntity.getStepsDistance());
        }
        Integer result = sysUserMapper.updateById(user);
        return result == 1;
    }

    /**
     * 对查询到的用户信息 空白信息 填充
     *
     * @param dto
     */
    private void userInfoFill(SysUserDTO dto) {
        if (StringUtils.isEmpty(dto.getEmail())) {
            dto.setEmail("");
        }
        if (StringUtils.isEmpty(dto.getPhoto())) {
            dto.setPhoto(AppConstant.DEFAULT_APP_HEADER);
        }
        if (StringUtils.isEmpty(dto.getMac())) {
            dto.setMac("");
        }
        if (StringUtils.isEmpty(dto.getGender())) {
            dto.setGender("");
        }
        if (StringUtils.isEmpty(dto.getAge())) {
            dto.setAge("");
        }
        if (StringUtils.isEmpty(dto.getWeight())) {
            dto.setWeight("");
        }
        if (StringUtils.isEmpty(dto.getStature())) {
            dto.setStature("");
        }
        if (StringUtils.isEmpty(dto.getBaid())) {
            dto.setBaid("");
        }
        if (StringUtils.isEmpty(dto.getBapid())) {
            dto.setBapid("");
        }
        if (StringUtils.isEmpty(dto.getBaName())) {
            dto.setBaName("");
        }
        if (StringUtils.isEmpty(dto.getBapName())) {
            dto.setBapName("");
        }
    }
}
