package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.OrgBaseInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IOrgBaseInfoService {

	public void save(OrgBaseInfoEntity result);
	
	public void update(OrgBaseInfoEntity result);
	
	public List<OrgBaseInfoEntity> findByEntity(OrgBaseInfoEntity entity);
}
