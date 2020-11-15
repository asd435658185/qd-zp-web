package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 评语管理查询
 * @author wdj
 *
 */

public class WebCommentQryDto extends WebBaseRequestDto {

	private String remark;
	
	private Long id;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
