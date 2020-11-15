package com.zhiyu.zp.service;

import java.util.List;

import com.zhiyu.zp.entity.ZpGradeSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpGradeSettingService {

	public List<ZpGradeSettingEntity> findByEntity(ZpGradeSettingEntity entity);
	
	/**
	 * 通过学校和年级di获取设置列表
	 * @param schoolId
	 * @param gradeId
	 * @return
	 */
	public ZpGradeSettingEntity findBySchoolIdAndGradeId(Long schoolId,Long gradeId);
	
	public void save(ZpGradeSettingEntity entity);
	
	public ZpGradeSettingEntity findById(Long id);
	
	public void update(ZpGradeSettingEntity entity);
}
