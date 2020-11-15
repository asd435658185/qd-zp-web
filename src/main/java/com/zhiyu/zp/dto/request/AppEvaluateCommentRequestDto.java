package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.AppBaseRequestDto;

/**
 * 评语的学生信息
 * @author wdj
 *
 */

public class AppEvaluateCommentRequestDto extends AppBaseRequestDto{

	private Long classId;
	
	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

}
