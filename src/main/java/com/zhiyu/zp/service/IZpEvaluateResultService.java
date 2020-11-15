package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpEvaluateResultEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpEvaluateResultService {

	/**
	 * 查询学生常规的数据状态正常的某次的次评信息
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentRegularNormalTimeEvaluate(Long studentId,Long schoolId,Long classId,Long evaluateId);
	
	/**
	 * 获取学生评价册的评价结果
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param bagId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentEvaluateBagResult(Long studentId,Long schoolId,Long classId,Long bagId);
	
	/**
	 * 查询学生常规的数据状态正常的某次的次评的总评信息
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentRegularNormalGatherEvaluate(Long studentId,Long schoolId,Long classId,Long evaluateId);
	
	public List<ZpEvaluateResultEntity> findByEntity(ZpEvaluateResultEntity entity);
	/**
	 * 保存评估结果
	 * @param entity
	 */
	public void save(ZpEvaluateResultEntity entity);
	
	public void update(ZpEvaluateResultEntity entity);
	
	/**
	 * 查询学生常规的数据状态正常的某次的次评的总评信息列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentRegularNormalGatherEvaluateList(Long studentId,Long schoolId,Long classId,Long evaluateId);
	
	/**
	 * 获取班级学生评价册的评价结果列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param bagId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentEvaluateBagResultList(Long schoolId,Long classId,Long bagId);
	
	/**
	 * 查询班级学生常规的数据状态正常的次评信息列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentRegularNormalTimeEvaluateList(Long schoolId,Long classId,Long evaluateId);
}
