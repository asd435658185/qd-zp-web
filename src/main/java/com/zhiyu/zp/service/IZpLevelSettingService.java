package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpLevelSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpLevelSettingService {

	/**
	 * @param gradeLevelSettingId
	 * @return
	 */
	public List<ZpLevelSettingEntity> findByLevelId(Long schoolId,Long levelId);
	
	public List<ZpLevelSettingEntity> findByEntity(ZpLevelSettingEntity entity);
	
	public void save(ZpLevelSettingEntity entity);
	
	public void update(ZpLevelSettingEntity entity);
	
	public ZpLevelSettingEntity findById(Long id);
	
	public void delete(Long id);
}
