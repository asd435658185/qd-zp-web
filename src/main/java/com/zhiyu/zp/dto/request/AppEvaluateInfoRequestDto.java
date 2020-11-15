package com.zhiyu.zp.dto.request;

import javax.validation.constraints.NotNull;

import com.zhiyu.zp.dto.common.AppBaseRequestDto;
import com.zhiyu.zp.enumcode.EvaluateMethod;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateInfoRequestDto extends AppBaseRequestDto{

	private Long classId;
	
	@NotNull
	private EvaluateMethod method;
	
	@NotNull
	private Long operaterId;
	
	private Long termId;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public EvaluateMethod getMethod() {
		return method;
	}

	public void setMethod(EvaluateMethod method) {
		this.method = method;
	}

	public Long getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}
	
	
}
