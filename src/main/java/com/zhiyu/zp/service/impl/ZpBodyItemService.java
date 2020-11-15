package com.zhiyu.zp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpBodyItemDao;
import com.zhiyu.zp.entity.ZpBodyItemEntity;
import com.zhiyu.zp.service.IZpBodyItemService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpBodyItemService implements IZpBodyItemService {

	@Autowired
	private IZpBodyItemDao dao;

	public ZpBodyItemEntity findById(Long id) {
		try {
			return dao.findObjectById(id);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public ZpBodyItemEntity findBySchoolEngCode(Long schoolId,String engCode){
		ZpBodyItemEntity entity = new ZpBodyItemEntity();
		entity.setEngCode(engCode);
		entity.setSchoolId(schoolId);
		entity.setUpdateTime(null);
		entity.setCreateTime(null);
		try {
			return dao.findByEntity(entity).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ZpBodyItemEntity> findByEntity(ZpBodyItemEntity e){
		try {
			return dao.findByEntity(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
