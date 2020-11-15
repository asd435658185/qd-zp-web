package com.zhiyu.zp.dto.request;

import javax.validation.constraints.NotNull;

import com.zhiyu.zp.dto.common.AppBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateInfoEventualReportRequestDto extends AppBaseRequestDto{

	@NotNull
	private Long classId;
	
	private Long termId;
	
	@NotNull
	private Long studentId;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	
}
