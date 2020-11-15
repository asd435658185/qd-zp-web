package com.zhiyu.zp.dto.request;

import java.util.List;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebCommentFuzzyQryDto extends WebBaseRequestDto{

	private List<String> keywords;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	
}
