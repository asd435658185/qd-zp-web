package com.zhiyu.zp.dto.response.app.report;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;
import com.zhiyu.zp.enumcode.EvaluateStatus;

/**
 * 终评的学生信息
 * @author wdj
 *
 */

public class EventualStudentInfoDto extends ZpStudentBaseInfoDto{

	private EvaluateStatus evaluateStatus = EvaluateStatus.NOT_EVALUATE;
	
	private List<StudentTimeEvaluateStatus> methodEvaluateStatus = Lists.newArrayList();

	public EvaluateStatus getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(EvaluateStatus evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

	public List<StudentTimeEvaluateStatus> getMethodEvaluateStatus() {
		return methodEvaluateStatus;
	}

	public void setMethodEvaluateStatus(List<StudentTimeEvaluateStatus> methodEvaluateStatus) {
		this.methodEvaluateStatus = methodEvaluateStatus;
	}

	
}
