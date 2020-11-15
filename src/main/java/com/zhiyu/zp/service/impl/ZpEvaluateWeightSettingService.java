package com.zhiyu.zp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateWeightSettingDao;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.service.IZpEvaluateWeightSettingService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateWeightSettingService implements IZpEvaluateWeightSettingService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateWeightSettingDao dao;
	
	/**
	 * 获取常规的次评评估权重列表
	 * @param bagId
	 * @param degreeId
	 * @return
	 */
	public List<ZpEvaluateWeightSettingEntity> findRegularTimeEvaluateWeightSetting(Long bagId,Long degreeId){
		ZpEvaluateWeightSettingEntity entity = new ZpEvaluateWeightSettingEntity();
		entity.setEvaluateBagId(bagId);
		entity.setDataState(DataState.NORMAL);
		entity.setDegreeType(DegreeType.REGULAR);
		entity.setDegreeId(degreeId);
		entity.setEvaluateType(EvaluateType.TIME_EVALUATE);
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询总评权重列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 获取常规的报告册终评估权重列表
	 * @param bagId
	 * @param degreeId
	 * @return
	 */
	public List<ZpEvaluateWeightSettingEntity> findRegularGatherEvaluateWeightSetting(Long bagId){
		ZpEvaluateWeightSettingEntity entity = new ZpEvaluateWeightSettingEntity();
		entity.setEvaluateBagId(bagId);
		entity.setDataState(DataState.NORMAL);
		entity.setDegreeType(DegreeType.REGULAR);
		entity.setEvaluateType(EvaluateType.TOTAL_EVALUATED);
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("查询次评权重列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}

	@Transactional
	public void save(ZpEvaluateWeightSettingEntity entity) {
		dao.save(entity);
	}

	@Transactional
	public void update(ZpEvaluateWeightSettingEntity entity) {
		dao.update(entity);
	}

	public ZpEvaluateWeightSettingEntity findById(Long id) {
		return dao.findObjectById(id);
	}
	
}
