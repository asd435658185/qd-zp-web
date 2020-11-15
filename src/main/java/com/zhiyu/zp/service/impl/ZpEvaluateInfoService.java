package com.zhiyu.zp.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateInfoDao;
import com.zhiyu.zp.entity.ZpEvaluateInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.service.IZpEvaluateInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateInfoService implements IZpEvaluateInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateInfoDao dao;
	
	/**
	 * 获取报告册下（bagId）-数据状态正常-生效的-次评信息列表
	 * @param bagId
	 * @return
	 */
	public List<ZpEvaluateInfoEntity> findRegularNormalUsableByBagId(Long bagId){
		ZpEvaluateInfoEntity entity = new ZpEvaluateInfoEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setEvaluateBagId(bagId);
		entity.setUseable(Useable.USEABLE);
		entity.setDegreeType(DegreeType.REGULAR);
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取正常数据列表出错：{}",e.getMessage());
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public ZpEvaluateInfoEntity findById(Long id){
		return dao.findObjectById(id);
	}

	/**
	 * 按条件查询（支持模糊查询）
	 * @param entity
	 * @param sortMap
	 * @return
	 * @throws Exception
	 */
	public List<ZpEvaluateInfoEntity> findListByCondition(String condition, Object[] params,
			Map<String, String> orderby) {
		return dao.findListByConditionWithNoPage(condition, params, orderby);
	}

	public void save(ZpEvaluateInfoEntity entity) {
		dao.save(entity);
	}

	public void update(ZpEvaluateInfoEntity entity) {
		dao.update(entity);
	}
}
