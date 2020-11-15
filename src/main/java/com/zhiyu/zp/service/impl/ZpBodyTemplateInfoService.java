package com.zhiyu.zp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpBodyTemplateInfoDao;
import com.zhiyu.zp.entity.ZpBodyTemplateInfoEntity;
import com.zhiyu.zp.service.IZpBodyTemplateInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpBodyTemplateInfoService implements IZpBodyTemplateInfoService {

	@Autowired
	private IZpBodyTemplateInfoDao dao;

	public ZpBodyTemplateInfoEntity findBySchool(Long schoolId) {
		ZpBodyTemplateInfoEntity e = new ZpBodyTemplateInfoEntity();
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
