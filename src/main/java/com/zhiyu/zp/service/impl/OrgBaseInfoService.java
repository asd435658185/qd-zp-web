package com.zhiyu.zp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zhiyu.zp.dao.IOrgBaseInfoDao;
import com.zhiyu.zp.entity.OrgBaseInfoEntity;
import com.zhiyu.zp.service.IOrgBaseInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class OrgBaseInfoService implements IOrgBaseInfoService {

	@Autowired
	private IOrgBaseInfoDao dao;

	public OrgBaseInfoEntity findById(Long id) {
		try {
			return dao.findObjectById(id);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public List<OrgBaseInfoEntity> findByEntity(OrgBaseInfoEntity e){
		try {
			return dao.findByEntity(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public void save(OrgBaseInfoEntity result) {
		// TODO Auto-generated method stub
		dao.save(result);
	}

	public void update(OrgBaseInfoEntity result) {
		// TODO Auto-generated method stub
		dao.update(result);
	}
}
