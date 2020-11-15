package com.zhiyu.zp.dto.response.app.info;

import com.zhiyu.zp.enumcode.EvaluateCompletedStatus;

/**
 * 教师对次评自评情况列表
 * @author wdj
 *
 */

public class TeacherEvaluateSituationDto extends EvaluateSituationBaseDto{

	private Integer percent;
	
	private EvaluateCompletedStatus evaluateCompletedStatus = EvaluateCompletedStatus.UNCOMPLETED;

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public EvaluateCompletedStatus getEvaluateCompletedStatus() {
		return evaluateCompletedStatus;
	}

	public void setEvaluateCompletedStatus(EvaluateCompletedStatus evaluateCompletedStatus) {
		this.evaluateCompletedStatus = evaluateCompletedStatus;
	}

	
}
