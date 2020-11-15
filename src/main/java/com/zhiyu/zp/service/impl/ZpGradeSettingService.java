package com.zhiyu.zp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpGradeSettingDao;
import com.zhiyu.zp.entity.ZpGradeSettingEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.service.IZpGradeSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpGradeSettingService implements IZpGradeSettingService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpGradeSettingDao dao;
	
	public List<ZpGradeSettingEntity> findByEntity(ZpGradeSettingEntity entity){
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询年级等级列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 通过学校和年级di获取设置列表
	 * @param schoolId
	 * @param gradeId
	 * @return
	 */
	public ZpGradeSettingEntity findBySchoolIdAndGradeId(Long schoolId,Long gradeId){
		try {
			ZpGradeSettingEntity entity = new ZpGradeSettingEntity();
			entity.setDataState(DataState.NORMAL);
			entity.setGradeId(gradeId);
			entity.setSchoolId(schoolId);
			List<ZpGradeSettingEntity> list = dao.findByEntity(entity);
			if(!list.isEmpty()){
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("查询年级等级列表出现异常："+e.getMessage());
		}
		return null;
	}

	@Transactional
	public void save(ZpGradeSettingEntity entity) {
		dao.save(entity);
	}
	
	@Transactional
	public void update(ZpGradeSettingEntity entity) {
		dao.update(entity);
	}

	public ZpGradeSettingEntity findById(Long id) {
		return dao.findObjectById(id);
	}
}
