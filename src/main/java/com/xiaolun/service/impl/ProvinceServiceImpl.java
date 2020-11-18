package com.xiaolun.service.impl;

import com.xiaolun.dao.ProvinceDao;
import com.xiaolun.entity.Province;
import com.xiaolun.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @time: 2020-11-17 15:06
 * @author: likunlun
 * @description: 实现类
 */
@Service
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	ProvinceDao provinceDao;

	@Override
	public List<Province> findProvinceByName(String ProvinceName) {
		return this.provinceDao.findProvinceByName(ProvinceName);
	}
}
