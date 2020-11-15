package com.zhiyu.zp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpMarkSubjectDao;
import com.zhiyu.zp.entity.ZpMarkSubjectEntity;
import com.zhiyu.zp.service.IZpMarkSubjectService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpMarkSubjectService implements IZpMarkSubjectService {

	@Autowired
	private IZpMarkSubjectDao dao;

	public ZpMarkSubjectEntity findById(Long id) {
		try {
			return dao.findObjectById(id);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public ZpMarkSubjectEntity findBySchoolEngCode(Long schoolId,String engCode){
		ZpMarkSubjectEntity entity = new ZpMarkSubjectEntity();
		entity.setEngCode(engCode);
		entity.setSchoolId(schoolId);
		entity.setUpdateTime(null);
		entity.setCreateTime(null);
		try {
			return dao.findByEntity(entity).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
