package com.zhiyu.zp.service;

import java.util.List;
import com.zhiyu.zp.entity.ZpBodyResultLevelEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpBodyResultLevelService {

	public List<ZpBodyResultLevelEntity> fingByEntity(ZpBodyResultLevelEntity e);
	
	public void saveOrEntity(ZpBodyResultLevelEntity e);
	
    public void save(ZpBodyResultLevelEntity result);
	
	public void update(ZpBodyResultLevelEntity result);
}
