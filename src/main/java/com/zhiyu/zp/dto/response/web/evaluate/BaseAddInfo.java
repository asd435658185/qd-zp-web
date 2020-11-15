package com.zhiyu.zp.dto.response.web.evaluate;

/**
 * 
 * @author wdj
 *
 */

public class BaseAddInfo extends BaseInfoDto{

	private int degreeType;
	
	private String degrees;//在degreeType为1的时候会有值

	public int getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(int degreeType) {
		this.degreeType = degreeType;
	}

	public String getDegrees() {
		return degrees;
	}

	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}
	
	
}
