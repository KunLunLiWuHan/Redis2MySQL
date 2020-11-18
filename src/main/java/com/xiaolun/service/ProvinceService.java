package com.xiaolun.service;

import com.xiaolun.entity.Province;

import java.util.List;

/**
 * @time: 2020-11-17 15:05
 * @author: likunlun
 * @description: 查询省份Service接口
 */
public interface ProvinceService {
	public List<Province> findProvinceByName(String ProvinceName);
}
