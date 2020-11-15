package com.zhiyu.zp.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dao.IZpResultCommentDao;
import com.zhiyu.zp.entity.ZpResultCommentEntity;
import com.zhiyu.zp.service.IZpResultCommentService;

/**
 * 
 * @author wdj
 *
 */
@Service
public class ZpResultCommentService implements IZpResultCommentService {

	@Autowired
	private IZpResultCommentDao dao;
	
	public List<ZpResultCommentEntity> findAll(Long schoolId){
		ZpResultCommentEntity e = new ZpResultCommentEntity();
		e.setSchoolId(schoolId);
		try {
			return dao.findByEntity(e);
		} catch (Exception e1) {
			return null;
		}
	}
	
	/**
	 * 按关键字查询
	 * @param keywords
	 * @return
	 */
	public List<ZpResultCommentEntity> findByKeywords(List<String> keywords,Long schoolId){
		if(keywords!=null){
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = keywords.iterator();
			List<Object> params = Lists.newArrayList();
			while(it.hasNext()){
				String key = it.next();
				if(StringUtils.isNotBlank(key)){
					sb.append(" and remark like ? ");
					params.add("%"+key+"%");
				}else{
					it.remove();
				}
			}
			sb.append(" and schoolId=? ");
			params.add(schoolId);
			return dao.findListByConditionWithNoPage(sb.toString(), params.toArray(), null);
		}
		return Lists.newArrayList();
	}
	
	public void save(ZpResultCommentEntity entity){
		dao.save(entity);
	}
	
	public void update(ZpResultCommentEntity entity){
		entity.setUpdateTime(new Date());
		dao.update(entity);
	}
	
	public void delete(Long id){
		dao.deleteObjectByIds(id);
	}
	
	public ZpResultCommentEntity findById(Long id){
		return dao.findObjectById(id);
	}
}
