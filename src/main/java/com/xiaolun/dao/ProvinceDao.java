package com.xiaolun.dao;

import com.xiaolun.entity.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @time: 2020-11-17 15:03
 * @author: likunlun
 * @description: Dao层接口，根据省份名查询省份ID
 */
@Mapper //推荐使用
public interface ProvinceDao {
	public List<Province> findProvinceByName(String ProvinceName);
}
