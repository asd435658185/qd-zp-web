package com.zhiyu.zp.dto.response.web.degree;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class BaseDegreeUpdateInfoDto {

	private String degreeName;
	
	private Long degreeId;
	
	private String degreeDesc;
	
	private int maxNum;
	
	private Integer versionId;
	
	private int status;
	
	private List<BaseDegreeUpdateInfoDto> items = Lists.newArrayList();
	
	public List<BaseDegreeUpdateInfoDto> getItems() {
		return items;
	}

	public void setItems(List<BaseDegreeUpdateInfoDto> items) {
		this.items = items;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
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

	
	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegreeDesc() {
		return degreeDesc;
	}

	public void setDegreeDesc(String degreeDesc) {
		this.degreeDesc = degreeDesc;
	}
	
}
