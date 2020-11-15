package com.zhiyu.zp.service;

import java.util.List;
import com.zhiyu.zp.entity.ZpRealisticEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpRealisticService {

	public void save(ZpRealisticEntity entity);
	
	public void update(ZpRealisticEntity entity);
	
	public void delete(Long id);
	
	public ZpRealisticEntity findById(Long id);
	
	public List<ZpRealisticEntity> findByEntity(ZpRealisticEntity entity);
}
