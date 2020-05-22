package cn.ac.ict.component;

import java.util.List;
import java.util.Set;

/**
 * Redis组件
 *
 * @author cuiweiman
 * @date 2020/5/9 13:09
 */
public interface RedisComponent {

    /**
     * @param key
     * @return
     * @Description 根据键通过get方法获取键对应值
     */
    String get(String key);

    /**
     * @param key
     * @return
     * @Description 根据键通过get方法获取键对应值
     */
    Object getObject(String key);

    /**
     * 通过过个key从redis中查询内容
     *
     * @param keys
     * @return
     */
    List<String> mGet(String... keys);

    /**
     * @param key
     * @param value
     * @return
     * @Description 通过set方法向缓存中插入新的键值对
     */
    Boolean set(String key, String value);

    /**
     * @param key
     * @param value
     * @return
     * @Description 通过set方法向缓存中插入新的键值对
     */
    Boolean setObject(String key, Object value, Integer outTime);


    /**
     * @param key
     * @param value
     * @param outTime 设定超时失效时间
     * @return
     * @Description 通过set方法向缓存中插入新的键值对
     */
    Boolean set(String key, String value, Integer outTime);

    /**
     * @param key
     * @return
     * @Description 通过键判断 key-value 是否存在
     */
    Boolean exists(String key);

    Boolean del(String key);

    /**
     * 原子long值
     *
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * @param perfix
     * @return:
     * @Description: 获取包含前缀perfix的所有的key值
     */
    Set<String> getAllKey(String perfix);

    /**
     * 针对指定的key设定过期时间
     *
     * @param key
     * @param seconds
     * @return
     */
    Integer expire(String key, Integer seconds);

}
