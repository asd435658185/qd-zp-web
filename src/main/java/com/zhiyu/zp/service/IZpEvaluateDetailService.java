package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.enumcode.EvaluateMethod;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateDetailService {

	/**
	 * 获取常规的结果明细列表
	 * @param resultId
	 * @param method
	 * @return
	 */
	public List<ZpEvaluateDetailEntity> findRegularResultDetailList(Long resultId,EvaluateMethod method,Long degreeId);
	
	/**
	 * 保存评估明细信息
	 * @param entity
	 */
	public void save(ZpEvaluateDetailEntity entity);
	
	/**
	 * 根据id查询记录
	 * @param id
	 * @return
	 */
	public ZpEvaluateDetailEntity findById(Long id);
	
	/**
	 * 更新信息
	 * @param entity
	 */
	public void update(ZpEvaluateDetailEntity entity);
}
