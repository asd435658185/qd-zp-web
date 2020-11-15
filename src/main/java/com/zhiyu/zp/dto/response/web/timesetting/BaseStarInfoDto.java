package com.zhiyu.zp.dto.response.web.timesetting;

/**
 * 次评星级信息
 * @author wdj
 *
 */

public class BaseStarInfoDto {

	private String degreeName;
	
	private Long degreeId;
	
	private boolean canSet;
	
	private Long bagId;
	
	private String bagName;

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

	public boolean isCanSet() {
		return canSet;
	}

	public void setCanSet(boolean canSet) {
		this.canSet = canSet;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}

	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}
}
