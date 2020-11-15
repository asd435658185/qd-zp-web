package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateWeightSettingService {

	/**
	 * 获取常规的次评评估权重列表
	 * @param bagId
	 * @param degreeId
	 * @return
	 */
	public List<ZpEvaluateWeightSettingEntity> findRegularTimeEvaluateWeightSetting(Long bagId,Long degreeId);
	
	/**
	 * 获取常规的报告册终评估权重列表
	 * @param bagId
	 * @param degreeId
	 * @return
	 */
	public List<ZpEvaluateWeightSettingEntity> findRegularGatherEvaluateWeightSetting(Long bagId);
	
	public void save(ZpEvaluateWeightSettingEntity entity);
	
	public void update(ZpEvaluateWeightSettingEntity entity);
	
	public ZpEvaluateWeightSettingEntity findById(Long id);
}
