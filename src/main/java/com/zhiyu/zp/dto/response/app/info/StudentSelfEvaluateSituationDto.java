package com.zhiyu.zp.dto.response.app.info;

import com.zhiyu.zp.enumcode.EvaluateStatus;

/**
 * 学生对次评自评情况列表
 * @author wdj
 *
 */

public class StudentSelfEvaluateSituationDto extends EvaluateSituationBaseDto{

	private EvaluateStatus evaluateStatus = EvaluateStatus.NOT_EVALUATE;

	public EvaluateStatus getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(EvaluateStatus evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}
	
	
}
