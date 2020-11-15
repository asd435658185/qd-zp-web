package com.zhiyu.zp.dto.request;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateInfoDegreeRequestDto extends AppEvaluateInfoRequestDto{

	private Long evaluateId;
	
	@NotNull
	private Long studentId;
	
	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
