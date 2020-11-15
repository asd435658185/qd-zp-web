package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpTermBaseInfoDao;
import com.zhiyu.zp.entity.ZpTermBaseInfoEntity;
import com.zhiyu.zp.service.IZpTermBaseInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpTermBaseInfoService implements IZpTermBaseInfoService {

	@Autowired
	private IZpTermBaseInfoDao dao;
	
	public List<ZpTermBaseInfoEntity> findByEntity(ZpTermBaseInfoEntity entity){
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}
	
	public void saveOrUpdate(ZpTermBaseInfoEntity entity){
		dao.saveOrUpdate(entity);
	}
	
	public ZpTermBaseInfoEntity findById(Long id){
		return dao.findObjectById(id);
	}
}
