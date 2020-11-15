package com.zhiyu.zp.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateDetailDao;
import com.zhiyu.zp.entity.ZpEvaluateDetailEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.service.IZpEvaluateDetailService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateDetailService implements IZpEvaluateDetailService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateDetailDao dao;
	
	/**
	 * 获取常规的结果明细列表
	 * @param resultId
	 * @param method
	 * @return
	 */
	public List<ZpEvaluateDetailEntity> findRegularResultDetailList(Long resultId,EvaluateMethod method,Long degreeId){
		ZpEvaluateDetailEntity entity = new ZpEvaluateDetailEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setCreateTime(null);
		entity.setUpdateTime(null);
		entity.setResultId(resultId);
		entity.setEvaluateMethod(method);
		if(degreeId!=null){
			entity.setDegreeId(degreeId);
		}
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取明细数据列表出错：{}",e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 保存评估明细信息
	 * @param entity
	 */
	public void save(ZpEvaluateDetailEntity entity){
		dao.save(entity);
	}

	public ZpEvaluateDetailEntity findById(Long id) {
		return dao.findObjectById(id);
	}

	public void update(ZpEvaluateDetailEntity entity) {
		dao.update(entity);
	}
}
