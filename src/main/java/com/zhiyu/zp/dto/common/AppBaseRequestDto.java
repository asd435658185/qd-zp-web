package com.zhiyu.zp.dto.common;

import javax.validation.constraints.NotNull;

/**
 * App基础请求数据对象
 * @author wdj
 *
 */

public class AppBaseRequestDto {

	@NotNull
	private Long schoolId;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
	
}
