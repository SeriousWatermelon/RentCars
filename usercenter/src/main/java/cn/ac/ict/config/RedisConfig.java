package cn.ac.ict.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@EnableAutoConfiguration
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;

    private int port;

    private String password;

    // 连接超时时长（毫秒）
    private int timeout;

    // 连接池最大连接数（使用负值表示没有限制）
    private int maxActive;

    // 连接池最大阻塞等待时间（使用负值表示没有限制）
    private int maxWait;

    // 连接池中的最大空闲连接
    private int maxIdle;

    // 连接池中的最小空闲连接
    private int minIdle;

    /**
     * @return 返回RedisPool 连接池
     * @Description Redis连接配置
     */
    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMinIdle(minIdle);

        JedisPool jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
        return jedisPool;
    }


}
