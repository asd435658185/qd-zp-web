package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebMarkQryDto extends WebBaseRequestDto {

	private Long gradeId;
	
	private Long classId;
	
	private Long termId;

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

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
	
}
