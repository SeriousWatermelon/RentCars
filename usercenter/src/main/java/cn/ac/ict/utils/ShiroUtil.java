package cn.ac.ict.utils;

import cn.ac.ict.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;


/**
 * Shiro工具类
 *
 * @author weiman cui
 * @date 2020/5/20 16:17
 */
public class ShiroUtil {


    /**
     * 加密算法
     */
    public final static String algorithmName = "SHA-256";

    /**
     * 加密散列次数
     */
    public static final int hashIterations = 1;


    /**
     * 加盐 加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String EncodeSalt(String password, String salt) {
        return new SimpleHash(algorithmName, password, salt, hashIterations).toString();
    }


    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static SysUserEntity getUserEntity() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getUserId() {
        return getUserEntity().getId();
    }
}
