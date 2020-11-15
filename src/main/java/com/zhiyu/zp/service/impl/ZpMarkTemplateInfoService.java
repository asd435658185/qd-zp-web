package com.zhiyu.zp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpMarkTemplateInfoDao;
import com.zhiyu.zp.entity.ZpMarkTemplateInfoEntity;
import com.zhiyu.zp.service.IZpMarkTemplateInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpMarkTemplateInfoService implements IZpMarkTemplateInfoService {

	@Autowired
	private IZpMarkTemplateInfoDao dao;

	public ZpMarkTemplateInfoEntity findBySchool(Long schoolId) {
		ZpMarkTemplateInfoEntity e = new ZpMarkTemplateInfoEntity();
		e.setSchoolId(schoolId);
		e.setUpdateTime(null);
		e.setCreateTime(null);
		try {
			return dao.findByEntity(e).get(0);
		} catch (Exception ex) {
			return null;
		}
	}
}
