package com.zhiyu.zp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateBagDao;
import com.zhiyu.zp.entity.ZpEvaluateBagEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.service.IZpEvaluateBagService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateBagService implements IZpEvaluateBagService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateBagDao dao;
	
	/**
	 * 新增
	 */
	@Transactional
	public void save(ZpEvaluateBagEntity entity){
		entity.setCreateTime(new Date());
		entity.setDataState(DataState.NORMAL);
		entity.setUseable(Useable.UNUSEABLE);
		dao.save(entity);
	}

	/**
	 * 更新
	 * @param entity
	 */
	@Transactional
	public void update(ZpEvaluateBagEntity entity){
		entity.setUpdateTime(new Date());
		dao.update(entity);
	}
	
	/**
	 * 查询所有数据状态为正常的记录
	 * @return
	 */
	public List<ZpEvaluateBagEntity> findAllNormal() {
		ZpEvaluateBagEntity entity = new ZpEvaluateBagEntity();
		entity.setDataState(DataState.NORMAL);
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取正常数据列表出错：{}",e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 按条件查询所有记录
	 * @return
	 */
	public List<ZpEvaluateBagEntity> findByEntity(ZpEvaluateBagEntity entity){
		try {
			entity.setDataState(DataState.NORMAL);
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取正常数据列表出错：{}",e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 通过id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateBagEntity findById(Long id){
		return dao.findObjectById(id);
	}
	
	/**
	 * 按条件查询（支持模糊查询）
	 * @param entity
	 * @param sortMap
	 * @return
	 * @throws Exception
	 */
	public List<ZpEvaluateBagEntity> findListByCondition(String condition, Object[] params, Map<String, String> orderby) {
		return dao.findListByConditionWithNoPage(condition, params, orderby);
	}
}
