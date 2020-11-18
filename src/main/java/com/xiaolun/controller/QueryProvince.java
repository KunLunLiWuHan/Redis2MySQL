package com.xiaolun.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolun.entity.Province;
import com.xiaolun.service.ProvinceService;
import com.xiaolun.util.JedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @time: 2020-11-17 14:50
 * @author: likunlun
 * @description: 根据省份名查询省份
 */
@RestController
public class QueryProvince {
	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private JedisPool jedisPool;

	@RequestMapping("/findProvince/{name}")
	public String find(@PathVariable("name") String name) {
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		//1、查询Redis，
		String provinceData = jedis.get(name);
		System.out.println("provinceData----->" + provinceData);
		//当Redis中没有数据的时候，此时需要查询数据库
		if (provinceData == null) {
			System.out.println("redis中没数据，查询数据库...");
			//查询数据库，返回相应的地名的信息
			List<Province> provinceNameList = this.provinceService.findProvinceByName(name);
			System.out.println("------------provinceName---------------" + provinceNameList);
			//2、将java对象转换为json字符串集合
			ObjectMapper mapper = new ObjectMapper();
			try {
				provinceData = mapper.writeValueAsString(provinceNameList);
				//将数据存入Redis中
				for (Province province : provinceNameList) {
					jedis.set(province.getName(), String.valueOf(province.getId()));
				}
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} finally {
				//3、释放Jedis资源
				JedisPoolUtil.release(jedisPool, jedis);
			}
			return provinceData;
		} else {
			System.out.println("redis中有数据，数据为：" + provinceData);
			//将从Redis中查询到数据返回到前端
			return provinceData;
		}

	}
}
