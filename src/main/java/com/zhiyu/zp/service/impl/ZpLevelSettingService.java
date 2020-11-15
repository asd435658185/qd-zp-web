package com.zhiyu.zp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpLevelSettingDao;
import com.zhiyu.zp.entity.ZpLevelSettingEntity;
import com.zhiyu.zp.service.IZpLevelSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpLevelSettingService implements IZpLevelSettingService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpLevelSettingDao dao;
	
	public List<ZpLevelSettingEntity> findByEntity(ZpLevelSettingEntity entity){
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询年级等级列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * @param levelId
	 * @return
	 */
	public List<ZpLevelSettingEntity> findByLevelId(Long schoolId,Long levelId){
		try {
			ZpLevelSettingEntity entity = new ZpLevelSettingEntity();
			entity.setLevelId(levelId);
			entity.setSchoolId(schoolId);
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询结果等级列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}

	public void save(ZpLevelSettingEntity entity) {
		dao.save(entity);
	}

	public void update(ZpLevelSettingEntity entity) {
		dao.update(entity);
	}

	public ZpLevelSettingEntity findById(Long id) {
		return dao.findObjectById(id);
	}

	public void delete(Long id) {
		dao.deleteObjectByIds(id);
	}
}
