package com.xiaolun.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @time: 2020-11-17 15:51
 * @author: likunlun
 * @description: Redis工具类
 */
public class JedisPoolUtil {
	//把Jedis的连接资源放到池子中。
	public static void release(JedisPool jedisPool, Jedis jedis) {
		if (null != jedis) {
			jedisPool.returnResourceObject(jedis);
		}
	}
}
