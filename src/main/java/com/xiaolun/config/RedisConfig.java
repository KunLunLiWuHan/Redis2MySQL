package com.xiaolun.config;

import com.xiaolun.util.JedisPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @time: 2020-11-17 16:08
 * @author: likunlun
 * @description: RedisConfig的配置
 */
@Configuration
@PropertySource("classpath:redis.properties")
@Slf4j
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private long maxWaitMillis;

	@Value("${spring.redis.block-when-exhausted}")
	private boolean blockWhenExhausted;

	private static volatile JedisPool jedisPool = null;

	@Bean //返回一个连接池
	public JedisPool getJedisPoolInstance() throws Exception {
		log.info("JedisPool注入成功！！");
		log.info("redis地址：" + host + ":" + port);

		if (null == jedisPool) {
			synchronized (RedisConfig.class) {
				if (null == jedisPool) {
					JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
					jedisPoolConfig.setMaxIdle(maxIdle);
					jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
					// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
					jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
					// 是否启用pool的jmx管理功能, 默认true
					jedisPoolConfig.setJmxEnabled(true);
					jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
				}
			}
		}
		return jedisPool;
	}
}
