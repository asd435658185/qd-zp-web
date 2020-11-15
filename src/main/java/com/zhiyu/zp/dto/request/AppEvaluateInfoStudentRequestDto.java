package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.enumcode.EvaluateStatus;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateInfoStudentRequestDto extends AppEvaluateInfoRequestDto{

	private Long evaluateId;
	
	private EvaluateStatus evaluateStatus;

	public EvaluateStatus getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(EvaluateStatus evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	
}
