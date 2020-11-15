package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.baseplatform.entity.ThirdPartyDockEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IThirdPartyDockService {

	public String findPartyMoralRoleId(Integer partyId,String thirdPartySchoolId);
	
	public List<ThirdPartyDockEntity> findNormalList(Integer partyId);
	
	public List<ThirdPartyDockEntity> findByEntity(ThirdPartyDockEntity entity);
	
	public ThirdPartyDockEntity merge(ThirdPartyDockEntity entity);
}
