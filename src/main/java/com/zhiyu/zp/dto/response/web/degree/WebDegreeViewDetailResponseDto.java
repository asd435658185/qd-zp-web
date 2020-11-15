package com.zhiyu.zp.dto.response.web.degree;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.enumcode.DegreeType;

/**
 * 
 * @author wdj
 *
 */

public class WebDegreeViewDetailResponseDto  extends BaseDegreeValidDto{

	private Long schoolId;
	
	private DegreeType degreeType;
	
	private Long degreePicId;
	
	private int status;
	
	private List<BaseDegreeUpdateInfoDto> items = Lists.newArrayList();

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
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
