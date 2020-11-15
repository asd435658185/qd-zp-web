package com.zhiyu.zp.service;

import java.util.List;
import java.util.Map;

import com.zhiyu.zp.entity.ZpEvaluateBagEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateBagService {

	/**
	 * 新增
	 * @param entity
	 */
	public void save(ZpEvaluateBagEntity entity);
	
	/**
	 * 查询所有数据状态为正常的记录
	 * @return
	 */
	public List<ZpEvaluateBagEntity> findAllNormal();
	
	/**
	 * 更新
	 * @param entity
	 */
	public void update(ZpEvaluateBagEntity entity);
	
	/**
	 * 按条件查询所有记录
	 * @return
	 */
	public List<ZpEvaluateBagEntity> findByEntity(ZpEvaluateBagEntity entity);
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateBagEntity findById(Long id);
	
	/**
	 * 按条件查询（支持模糊查询）
	 * @param entity
	 * @param sortMap
	 * @return
	 * @throws Exception
	 */
	public List<ZpEvaluateBagEntity> findListByCondition(String condition, Object[] params, Map<String, String> orderby);
}
