package com.xiaolun;

import com.xiaolun.util.JedisPoolUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @time: 2020-11-17 16:20
 * @author: likunlun
 * @description: 操作Redis测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MainApp.class})
public class RedisTest {
	@Autowired
	private JedisPool jedisPool;

	@Test
	public void connect() {
		System.out.println("--------开始测试-----");

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set("aa", "bb");
			Thread.sleep(1000);

			String key = jedis.get("aa");
			System.out.println("------key为aa的值为-------" + key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JedisPoolUtil.release(jedisPool, jedis);
		}
	}
}
