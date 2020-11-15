package com.zhiyu.zp.service;

import java.util.List;
import com.zhiyu.zp.entity.ZpEvaluateDegreeVersionEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateDegreeVersionService {
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateDegreeVersionEntity findById(Long id);
	/**
	 * 按条件查询所有对象
	 * @return
	 */
	public List<ZpEvaluateDegreeVersionEntity> findByEntity(ZpEvaluateDegreeVersionEntity entity);
	/**
	 * 保存对象
	 * @param entity
	 */
	public void save(ZpEvaluateDegreeVersionEntity entity);
	
	/**
	 * 更新对象
	 * @param entity
	 */
	public void update(ZpEvaluateDegreeVersionEntity entity);
	
	/**
	 * 删除对象
	 * @param id
	 */
	public void delete(Long id);
}
