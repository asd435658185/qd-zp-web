package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpMarkResultEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpMarkResultService {

	public void save(ZpMarkResultEntity result);
	
	public void update(ZpMarkResultEntity result);
	
	public List<ZpMarkResultEntity> findByEntity(ZpMarkResultEntity entity);
}
