package com.zhiyu.zp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpRealisticDao;
import com.zhiyu.zp.entity.ZpRealisticEntity;
import com.zhiyu.zp.service.IZpRealisticService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpRealisticService implements IZpRealisticService{

	@Autowired
	private IZpRealisticDao dao;
	
	public void save(ZpRealisticEntity entity) {
		// TODO Auto-generated method stub
		dao.save(entity);
	}

	public void update(ZpRealisticEntity entity) {
		// TODO Auto-generated method stub
		entity.setUpdateTime(new Date());
		dao.update(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		dao.deleteObjectByIds(id);
	}

	public ZpRealisticEntity findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findObjectById(id);
	}

	public List<ZpRealisticEntity> findByEntity(ZpRealisticEntity entity) {
		// TODO Auto-generated method stub
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

}
