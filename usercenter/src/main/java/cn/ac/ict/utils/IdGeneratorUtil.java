package cn.ac.ict.utils;

import java.util.UUID;

/**
 * id生成工具
 *
 * @author cuiweiman
 * @date 2020/5/7 18:21
 */
public class IdGeneratorUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }



}
