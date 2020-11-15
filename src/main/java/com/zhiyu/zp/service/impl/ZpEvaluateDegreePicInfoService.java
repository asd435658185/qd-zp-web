package com.zhiyu.zp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhiyu.zp.dao.IZpEvaluateDegreePicInfoDao;
import com.zhiyu.zp.entity.ZpEvaluateDegreePicInfoEntity;
import com.zhiyu.zp.service.IZpEvaluateDegreePicInfoService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateDegreePicInfoService implements IZpEvaluateDegreePicInfoService {

	@Autowired
	private IZpEvaluateDegreePicInfoDao dao;
	
	public ZpEvaluateDegreePicInfoEntity findById(Long id){
		return dao.findObjectById(id);
	}
}
