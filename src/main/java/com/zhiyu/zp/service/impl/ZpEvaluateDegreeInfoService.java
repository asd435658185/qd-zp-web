package com.zhiyu.zp.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateDegreeInfoDao;
import com.zhiyu.zp.entity.ZpEvaluateDegreeInfoEntity;
import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.HasChildren;
import com.zhiyu.zp.enumcode.Useable;
import com.zhiyu.zp.service.IZpEvaluateDegreeInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateDegreeInfoService implements IZpEvaluateDegreeInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IZpEvaluateDegreeInfoDao dao;

	/**
	 * 获取学校常规的维度列表,根据条件可筛选出生效和未生效的
	 * 
	 * @param schoolId
	 * @return
	 */
	public List<ZpEvaluateDegreeInfoEntity> findRegular(Long schoolId, Useable usable, 
			DataState dataState,Integer versionId) {
		ZpEvaluateDegreeInfoEntity entity = new ZpEvaluateDegreeInfoEntity();
		if (usable != null) {
			entity.setUseable(usable);
		}
		entity.setDataState(dataState);
		entity.setDegreeType(DegreeType.REGULAR);
		entity.setSchoolId(schoolId);
		if(versionId != null){
			entity.setVersionId(versionId);	
		}
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取正常数据列表出错：{}", e.getMessage());
		}
		return Lists.newArrayList();
	}

	/**
	 * 获取学校第一条叶子级维度信息
	 * 
	 * @param schoolId
	 * @return
	 */
	public ZpEvaluateDegreeInfoEntity findFirstLeafDegree(Long schoolId) {
		ZpEvaluateDegreeInfoEntity entity = new ZpEvaluateDegreeInfoEntity();
		entity.setDataState(DataState.NORMAL);
		entity.setHasChildren(HasChildren.DONT_HAVE_CHILD);
		entity.setDegreeType(DegreeType.REGULAR);
		entity.setSchoolId(schoolId);
		try {
			return dao.findByEntity(entity).get(0);
		} catch (Exception e) {
			logger.error("获取正常数据列表出错：{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 通过id获取对象
	 * 
	 * @param id
	 * @return
	 */
	public ZpEvaluateDegreeInfoEntity findById(Long id) {
		return dao.findObjectById(id);
	}

	/**
	 * 通过父id，获取子维度
	 * 
	 * @param pid
	 * @return
	 */
	public List<ZpEvaluateDegreeInfoEntity> findByPid(Long pid) {
		ZpEvaluateDegreeInfoEntity entity = new ZpEvaluateDegreeInfoEntity();
		entity.setPid(pid);
		entity.setDataState(DataState.NORMAL);
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			logger.error("获取正常子数据列表出错：{}", e.getMessage());
		}
		return Lists.newArrayList();
	}

	/**
	 * 保存维度
	 * 
	 * @param entity
	 */
	@Transactional
	public void save(ZpEvaluateDegreeInfoEntity entity) {
		dao.save(entity);
	}

	/**
	 * 更新维度
	 * 
	 * @param entity
	 */
	@Transactional
	public void update(ZpEvaluateDegreeInfoEntity entity) {
		dao.update(entity);
	}

	/**
	 * 删除维度
	 * 
	 * @param id
	 */
	@Transactional
	public void delete(Long id) {
		dao.deleteObjectByIds(id);
	}

	public List<ZpEvaluateDegreeInfoEntity> findByEntity(ZpEvaluateDegreeInfoEntity entity) {
		// TODO Auto-generated method stub
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}
}
