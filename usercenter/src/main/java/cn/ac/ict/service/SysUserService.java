package cn.ac.ict.service;

import cn.ac.ict.dto.SysUserDTO;
import cn.ac.ict.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUserEntity> {


    /**
     * app登录
     *
     * @param phone
     * @param password
     * @return
     */
    SysUserDTO appLogin(String phone, String password);

    /**
     * app注册
     *
     * @param phone
     * @param password
     * @return
     */
    Boolean appRegister(String phone, String password);


    /**
     * 连表查询用户信息，以及 所属机构部门、绑定的mac
     *
     * @param userId
     * @return
     */
    SysUserDTO getUserByUserId(String userId);


    /**
     * 根据 账户名 查询用户信息
     *
     * @param loginName
     * @return
     */
    SysUserEntity getUserByLoginName(String loginName);

    /**
     * 根据部门ID或者用户姓名 查询用户信息
     *
     * @param baId
     * @param userName
     * @return
     */
    List<SysUserDTO> listUserByBaIdAndUserName(String baId, String userName);


    /**
     * 修改用户密码
     *
     * @param phone
     * @param password
     * @return
     */
    Boolean updatePassword(String phone, String password);


    /**
     * 修改 用户信息
     *
     * @param sysUserEntity
     * @return
     */
    Boolean updateUser(SysUserEntity sysUserEntity);

}
