package com.zhiyu.zp.service;

import com.zhiyu.zp.entity.ZpMarkTemplateInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public interface IZpMarkTemplateInfoService {

	public ZpMarkTemplateInfoEntity findBySchool(Long schoolId);
}
