package com.zhiyu.zp.service;

import com.zhiyu.zp.entity.ZpMarkSubjectEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpMarkSubjectService {

	public ZpMarkSubjectEntity findById(Long id);
	
	public ZpMarkSubjectEntity findBySchoolEngCode(Long schoolId,String engCode);
}
