package com.zhiyu.zp.dto.response.web.degree;

/**
 * 
 * @author wdj
 *
 */

public class BaseDegreeInfoDto {

	private String degreeName;
	
	private Long degreeId;
	
	private Integer versionId; 

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

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	
}
