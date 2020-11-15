package com.zhiyu.zp.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpEvaluateDegreeVersionDao;
import com.zhiyu.zp.entity.ZpEvaluateDegreeVersionEntity;
import com.zhiyu.zp.service.IZpEvaluateDegreeVersionService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpEvaluateDegreeVersionService  implements IZpEvaluateDegreeVersionService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IZpEvaluateDegreeVersionDao dao;
	
	public ZpEvaluateDegreeVersionEntity findById(Long id) {
		// TODO Auto-generated method stub
		return dao.findObjectById(id);
	}

	public void save(ZpEvaluateDegreeVersionEntity entity) {
		// TODO Auto-generated method stub
		dao.save(entity);
	}

	public void update(ZpEvaluateDegreeVersionEntity entity) {
		// TODO Auto-generated method stub
		dao.update(entity);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		dao.deleteObjectByIds(id);
	}

	public List<ZpEvaluateDegreeVersionEntity> findByEntity(ZpEvaluateDegreeVersionEntity entity) {
		// TODO Auto-generated method stub
		try {
			return dao.findByEntity(entity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取正常数据列表出错：{}",e.getMessage());
		}
		return Lists.newArrayList();
	}

}
