package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpBodyResultEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpBodyResultService {

	public void save(ZpBodyResultEntity result);
	
	public void update(ZpBodyResultEntity result);
	
	public List<ZpBodyResultEntity> findByEntity(ZpBodyResultEntity entity);
}
