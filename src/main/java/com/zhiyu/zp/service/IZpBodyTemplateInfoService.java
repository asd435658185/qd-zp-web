package com.zhiyu.zp.service;

import com.zhiyu.zp.entity.ZpBodyTemplateInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpBodyTemplateInfoService {

	public ZpBodyTemplateInfoEntity findBySchool(Long schoolId);
}
