package com.zhiyu.zp.dto.response.app.degree;

/**
 * 
 * @author wdj
 *
 */

public class EvaluateDegreeDto extends BaseEvaluateDegreeDto{

	private Long degreePicId;

	private String degreePicUrl;
	
	private String degreeColor;

	public Long getDegreePicId() {
		return degreePicId;
	}

	public void setDegreePicId(Long degreePicId) {
		this.degreePicId = degreePicId;
	}

	public String getDegreePicUrl() {
		return degreePicUrl;
	}

	public void setDegreePicUrl(String degreePicUrl) {
		this.degreePicUrl = degreePicUrl;
	}

	public String getDegreeColor() {
		return degreeColor;
	}

	public void setDegreeColor(String degreeColor) {
		this.degreeColor = degreeColor;
	}
	
	
}
