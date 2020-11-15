package com.zhiyu.zp.dto.response.web.timesetting;

/**
 * 
 * @author wdj
 *
 */

public class BaseUpdateDto {

	private Long id;
	
	private Long evaluateBagId;
	
	private Long degreeId;
	
	private Integer maxValue;
	
	private Integer minValue;
	
	private Integer resValue; 
	
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEvaluateBagId() {
		return evaluateBagId;
	}

	public void setEvaluateBagId(Long evaluateBagId) {
		this.evaluateBagId = evaluateBagId;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public Integer getResValue() {
		return resValue;
	}

	public void setResValue(Integer resValue) {
		this.resValue = resValue;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
