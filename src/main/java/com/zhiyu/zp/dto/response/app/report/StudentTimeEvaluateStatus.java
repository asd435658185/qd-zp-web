package com.zhiyu.zp.dto.response.app.report;

import com.zhiyu.zp.enumcode.EvaluateMethod;
import com.zhiyu.zp.enumcode.EvaluateStatus;

/**
 * 
 * @author wdj
 *
 */

public class StudentTimeEvaluateStatus {

	private EvaluateMethod method;
	
	private EvaluateStatus evaluteStatus;
	
	public StudentTimeEvaluateStatus(){}

	public StudentTimeEvaluateStatus(EvaluateMethod method) {
		super();
		this.method = method;
	}

	public EvaluateMethod getMethod() {
		return method;
	}

	public void setMethod(EvaluateMethod method) {
		this.method = method;
	}

	public EvaluateStatus getEvaluteStatus() {
		return evaluteStatus;
	}

	public void setEvaluteStatus(EvaluateStatus evaluteStatus) {
		this.evaluteStatus = evaluteStatus;
	}
	
	
}
