package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpMarkResultDao;
import com.zhiyu.zp.entity.ZpMarkResultEntity;
import com.zhiyu.zp.service.IZpMarkResultService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpMarkResultService implements IZpMarkResultService {

	@Autowired
	private IZpMarkResultDao dao;

	public void save(ZpMarkResultEntity result) {
		dao.save(result);
	}
	
	public void update(ZpMarkResultEntity result) {
		dao.update(result);
	}
	
	public List<ZpMarkResultEntity> findByEntity(ZpMarkResultEntity entity){
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
