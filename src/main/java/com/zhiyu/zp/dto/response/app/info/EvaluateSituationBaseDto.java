package com.zhiyu.zp.dto.response.app.info;

import java.util.Date;

/**
 * 
 * @author wdj
 *
 */

public class EvaluateSituationBaseDto {

	private Long evaluateId;
	
	private String evaluateName;
	
	private Date endDate;
	
	private Date beginDate;

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	
}
