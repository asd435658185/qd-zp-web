package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.Useable;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateDegreeInfoService {

	/**
	 * 获取学校常规的维度列表,根据条件可筛选出生效和未生效的
	 * @param schoolId
	 * @return
	 */
	public List<ZpEvaluateDegreeInfoEntity> findRegular(Long schoolId,Useable usable
			,DataState dataState,Integer versionId);
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateDegreeInfoEntity findById(Long id);
	
	/**
	 * 通过父id，获取子维度
	 * @param pid
	 * @return
	 */
	public List<ZpEvaluateDegreeInfoEntity> findByPid(Long pid);
	
	/**
	 * 保存维度
	 * @param entity
	 */
	public void save(ZpEvaluateDegreeInfoEntity entity);
	
	/**
	 * 更新维度
	 * @param entity
	 */
	public void update(ZpEvaluateDegreeInfoEntity entity);
	
	/**
	 * 删除维度
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 获取学校第一条叶子级维度信息
	 * 
	 * @param schoolId
	 * @return
	 */
	public ZpEvaluateDegreeInfoEntity findFirstLeafDegree(Long schoolId);
	
	public List<ZpEvaluateDegreeInfoEntity> findByEntity(ZpEvaluateDegreeInfoEntity entity);
}
