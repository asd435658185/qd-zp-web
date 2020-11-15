package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpTermBaseInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpTermBaseInfoService {

	public List<ZpTermBaseInfoEntity> findByEntity(ZpTermBaseInfoEntity entity);
	
	public void saveOrUpdate(ZpTermBaseInfoEntity entity);
	
	public ZpTermBaseInfoEntity findById(Long id);
}
