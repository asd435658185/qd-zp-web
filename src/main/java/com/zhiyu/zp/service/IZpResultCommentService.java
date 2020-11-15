package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpResultCommentEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpResultCommentService {

	public List<ZpResultCommentEntity> findAll(Long schoolId);
	
	public void save(ZpResultCommentEntity entity);
	
	public void update(ZpResultCommentEntity entity);
	
	public void delete(Long id);
	
	public ZpResultCommentEntity findById(Long id);
	
	/**
	 * 按关键字查询
	 * @param keywords
	 * @return
	 */
	public List<ZpResultCommentEntity> findByKeywords(List<String> keywords,Long schoolId);
}
