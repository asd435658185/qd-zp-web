package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpSchoolSettingDao;
import com.zhiyu.zp.entity.ZpSchoolSettingEntity;
import com.zhiyu.zp.service.IZpSchoolSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpSchoolSettingService implements IZpSchoolSettingService {

	@Autowired
	private IZpSchoolSettingDao dao;
	
	public List<ZpSchoolSettingEntity> findAll() {
		return dao.findAll();
	}

}
