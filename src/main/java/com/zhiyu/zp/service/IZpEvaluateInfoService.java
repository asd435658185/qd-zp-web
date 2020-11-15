package com.zhiyu.zp.service;

import java.util.List;
import java.util.Map;

import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateInfoService {

	/**
	 * 获取报告册下（bagId）-数据状态正常-生效的-次评信息列表
	 * @param bagId
	 * @return
	 */
	public List<ZpEvaluateInfoEntity> findRegularNormalUsableByBagId(Long bagId);
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateInfoEntity findById(Long id);
	
	/**
	 * 按条件查询（支持模糊查询）
	 * @param entity
	 * @param sortMap
	 * @return
	 * @throws Exception
	 */
	public List<ZpEvaluateInfoEntity> findListByCondition(String condition, Object[] params, Map<String, String> orderby);
	
	/**
	 * 保存
	 * @param entity
	 */
	public void save(ZpEvaluateInfoEntity entity);
	
	/**
	 * 更新
	 * @param entity
	 */
	public void update(ZpEvaluateInfoEntity entity);

}
