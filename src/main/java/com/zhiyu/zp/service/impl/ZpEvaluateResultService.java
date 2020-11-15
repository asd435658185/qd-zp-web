package com.zhiyu.zp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateResultDao;
import com.zhiyu.zp.entity.ZpEvaluateResultEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.enumcode.ResultType;
import com.zhiyu.zp.service.IZpEvaluateResultService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateResultService implements IZpEvaluateResultService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateResultDao dao;
	
	/**
	 * 查询学生常规的数据状态正常的某次的次评信息
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentRegularNormalTimeEvaluate(Long studentId,Long schoolId,Long classId,Long evaluateId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TIME_EVALUATE);
		entity.setBagEvaluateFlag(false);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList.get(0);
		} catch (Exception e) {
			logger.error("查询次评结果列表出现异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询班级学生常规的数据状态正常的次评信息列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentRegularNormalTimeEvaluateList(Long schoolId,Long classId,Long evaluateId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(null);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TIME_EVALUATE);
		entity.setBagEvaluateFlag(false);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList;
		} catch (Exception e) {
			logger.error("查询次评结果列表出现异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取学生评价册的评价结果
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param bagId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentEvaluateBagResult(Long studentId,Long schoolId,Long classId,Long bagId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(bagId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setBagEvaluateFlag(true);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList.get(0);
		} catch (Exception e) {
			logger.error("查询评价包结果列表出现异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 获取班级学生评价册的评价结果列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @param bagId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentEvaluateBagResultList(Long schoolId,Long classId,Long bagId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(null);//这里对学生参数设空
		entity.setEvaluateId(bagId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setBagEvaluateFlag(true);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList;
		} catch (Exception e) {
			logger.error("查询评价包结果列表出现异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询学生常规的数据状态正常的某次的次评的总评信息
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public ZpEvaluateResultEntity findStudentRegularNormalGatherEvaluate(Long studentId,Long schoolId,Long classId,Long evaluateId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TOTAL_EVALUATED);
		entity.setBagEvaluateFlag(false);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList.get(0);
		} catch (Exception e) {
			logger.error("查询次评的总评信息结果列表出现异常："+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询学生常规的数据状态正常的某次的次评的总评信息列表
	 * @param studentId
	 * @param schoolId
	 * @param classId
	 * @return
	 */
	public List<ZpEvaluateResultEntity> findStudentRegularNormalGatherEvaluateList(Long studentId,Long schoolId,Long classId,Long evaluateId){
		ZpEvaluateResultEntity entity = new ZpEvaluateResultEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setClassId(classId);
		entity.setSchoolId(schoolId);
		entity.setStudentId(studentId);
		entity.setEvaluateId(evaluateId);
		entity.setResultType(ResultType.EVALUATE);
		entity.setEvaluateType(EvaluateType.TOTAL_EVALUATED);
		entity.setBagEvaluateFlag(false);
		try {
			List<ZpEvaluateResultEntity> entityList = dao.findByEntity(entity);
			return entityList;
		} catch (Exception e) {
			logger.error("查询次评的总评信息结果列表出现异常："+e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 保存评估结果
	 * @param entity
	 */
	public void save(ZpEvaluateResultEntity entity){
		dao.save(entity);
	}

	public List<ZpEvaluateResultEntity> findByEntity(ZpEvaluateResultEntity entity) {
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void update(ZpEvaluateResultEntity entity) {
		// TODO Auto-generated method stub
		dao.update(entity);
	}
}
