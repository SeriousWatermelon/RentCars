package cn.ac.ict.mapper;

import cn.ac.ict.dto.SysUserDTO;
import cn.ac.ict.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUserEntity> {


    /**
     * 根据 手机号码 查询用户信息
     *
     * @param phone
     * @return
     */
    SysUserDTO getUserByPhone(@Param("phone") String phone);

    /**
     * 连表查询用户信息，以及 所属机构部门、绑定的mac
     *
     * @param userId
     * @return
     */
    SysUserDTO getUserByUserId(@Param("userId") String userId);


    /**
     * 根据部门ID或者用户姓名 查询用户信息
     *
     * @param baId
     * @param userName
     * @return
     */
    List<SysUserDTO> listUserByBaIdAndUserName(@Param("baId") String baId, @Param("userName") String userName);
}
