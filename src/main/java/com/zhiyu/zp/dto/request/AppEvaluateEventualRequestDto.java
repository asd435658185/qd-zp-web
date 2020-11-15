package com.zhiyu.zp.dto.request;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateEventualRequestDto extends AppEvaluateCommentRequestDto{

	private Long termId;

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}
	
	
}
