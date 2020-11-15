package com.zhiyu.zp.dto.response.web.evaluate;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class BaseInfoUpdateUseableDto extends WebBaseRequestDto{

	private Long evaluateId;

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	
}
