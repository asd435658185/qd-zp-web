package com.zhiyu.zp.dto.response.app.comment;

import com.zhiyu.zp.dto.common.ZpStudentBaseInfoDto;
import com.zhiyu.zp.enumcode.EvaluateStatus;

/**
 * 评语的学生信息
 * @author wdj
 *
 */

public class EvaluateCommentStudentInfoDto extends ZpStudentBaseInfoDto{

	private EvaluateStatus evaluateStatus = EvaluateStatus.NOT_EVALUATE;

    private String prize;
	
	private String feedback;
	
	public EvaluateStatus getEvaluateStatus() {
		return evaluateStatus;
	}

	public void setEvaluateStatus(EvaluateStatus evaluateStatus) {
		this.evaluateStatus = evaluateStatus;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
