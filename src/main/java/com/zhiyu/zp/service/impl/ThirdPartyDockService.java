package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.baseplatform.dao.IThirdPartyDockDao;
import com.zhiyu.baseplatform.entity.ThirdPartyDockEntity;
import com.zhiyu.baseplatform.enumcode.DataState;
import com.zhiyu.zp.service.IThirdPartyDockService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ThirdPartyDockService implements IThirdPartyDockService {

	@Autowired
	private IThirdPartyDockDao dao;
	
	public List<ThirdPartyDockEntity> findNormalList(Integer partyId) {
		ThirdPartyDockEntity e = new ThirdPartyDockEntity();
		e.setPartyId(partyId);
		e.setDataState(DataState.NORMAL);
		try {
			return dao.findByEntity(e);
		} catch (Exception e1) {
			throw new RuntimeException("没有第三方平台对接学校数据");
		}
	}
	
	public String findPartyMoralRoleId(Integer partyId,String thirdPartySchoolId){
		List<ThirdPartyDockEntity> list = findNormalList(partyId);
		if(list!=null && !list.isEmpty()){
			for(ThirdPartyDockEntity party:list){
				if(party.getEnterSchoolId().equals(thirdPartySchoolId)){
					return party.getMoralRoleId();
				}
			}
		}
		return null;
	}

	public List<ThirdPartyDockEntity> findByEntity(ThirdPartyDockEntity entity) {
		// TODO Auto-generated method stub
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ThirdPartyDockEntity merge(ThirdPartyDockEntity entity) {
		// TODO Auto-generated method stub
		return dao.merge(entity);
	}

}
