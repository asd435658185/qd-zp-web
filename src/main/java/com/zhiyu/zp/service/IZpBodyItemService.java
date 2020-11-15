package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpBodyItemEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpBodyItemService {

	public ZpBodyItemEntity findById(Long id);
	
	public ZpBodyItemEntity findBySchoolEngCode(Long schoolId,String engCode);
	
	public List<ZpBodyItemEntity> findByEntity(ZpBodyItemEntity e);
}
