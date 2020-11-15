package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebBagQryDto extends WebBaseRequestDto{

	private String bagName;
	
	private Long bagId;
	
    private Integer versionId;
	
	private String gradeIds; 

	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}
	
}
