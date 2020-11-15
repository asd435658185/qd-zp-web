package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 评语的学生信息
 * @author wdj
 *
 */

public class WebEvaluateCommentRequestDto extends WebBaseRequestDto{

	private Long classId;
	
	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

}
