package com.zhiyu.zp.dto.request;

/**
 * 评语提交的学生信息
 * @author wdj
 *
 */

public class AppEvaluateCommentCommitRequestDto extends AppEvaluateCommentRequestDto{

	private Long studentId;
	
	private String prize;
	
	private String feedback;

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	
}
