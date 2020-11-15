package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpBodyResultLevelDao;
import com.zhiyu.zp.entity.ZpBodyResultLevelEntity;
import com.zhiyu.zp.service.IZpBodyResultLevelService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpBodyResultLevelService implements IZpBodyResultLevelService {

	@Autowired
	private IZpBodyResultLevelDao dao;
	
	public List<ZpBodyResultLevelEntity> fingByEntity(ZpBodyResultLevelEntity e){
		try {
			return dao.findByEntity(e);
		} catch (Exception e1) {
			
		}
		return Lists.newArrayList();
	}
	
	public void saveOrEntity(ZpBodyResultLevelEntity e){
		dao.saveOrUpdate(e);
	}

	public void save(ZpBodyResultLevelEntity result) {
		// TODO Auto-generated method stub
		dao.save(result);
	}

	public void update(ZpBodyResultLevelEntity result) {
		// TODO Auto-generated method stub
		dao.update(result);
	}
}
