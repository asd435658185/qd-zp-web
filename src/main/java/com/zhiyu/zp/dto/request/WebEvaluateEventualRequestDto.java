package com.zhiyu.zp.dto.request;

/**
 * 
 * @author wdj
 *
 */

public class WebEvaluateEventualRequestDto extends WebEvaluateCommentRequestDto{

	private Long termId;
	
	private Long evaluateId;
	
	private Integer searchType = 0; //默认是次评查询   2表示终评查询

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	
	
	
}
