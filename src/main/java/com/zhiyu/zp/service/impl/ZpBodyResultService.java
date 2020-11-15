package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpBodyResultDao;
import com.zhiyu.zp.entity.ZpBodyResultEntity;
import com.zhiyu.zp.service.IZpBodyResultService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpBodyResultService implements IZpBodyResultService {

	@Autowired
	private IZpBodyResultDao dao;

	public void save(ZpBodyResultEntity result) {
		dao.save(result);
	}
	
	public void update(ZpBodyResultEntity result) {
		dao.update(result);
	}
	
	public List<ZpBodyResultEntity> findByEntity(ZpBodyResultEntity entity){
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
