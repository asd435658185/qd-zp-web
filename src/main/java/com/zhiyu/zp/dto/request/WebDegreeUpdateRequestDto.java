package com.zhiyu.zp.dto.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeUpdateInfoDto;
import com.zhiyu.zp.dto.response.web.degree.BaseDegreeValidDto;

/**
 * 
 * @author wdj
 *
 */

public class WebDegreeUpdateRequestDto extends BaseDegreeValidDto{

	private Long schoolId;
	
	private int degreeType;
	
	private Long degreePicId;
	
	private Integer versionId;
	
	private int status;
	
	private List<BaseDegreeUpdateInfoDto> items = Lists.newArrayList();

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public int getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(int degreeType) {
		this.degreeType = degreeType;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<BaseDegreeUpdateInfoDto> getItems() {
		return items;
	}

	public void setItems(List<BaseDegreeUpdateInfoDto> items) {
		this.items = items;
	}

	public Long getDegreePicId() {
		return degreePicId;
	}

	public void setDegreePicId(Long degreePicId) {
		this.degreePicId = degreePicId;
	}

	
}
