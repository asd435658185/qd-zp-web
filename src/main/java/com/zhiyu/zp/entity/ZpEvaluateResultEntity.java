package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateType;
import com.zhiyu.zp.enumcode.ResultType;

/**
 * 评估结果
 * @author wdj
 *
 */
@Table(name="zp_evaluate_result")
@Entity
public class ZpEvaluateResultEntity extends BaseEntity{

	private Long schoolId;
	
	private Long studentId;
	
	private Long classId;
	
	private Integer value;
	
	private Long levelId;
	
	private String levelName;
	
	private EvaluateType evaluateType;
	
	private Long evaluateId;
	
	private String prize;
	
	private String feedback;
	
	private DataState dataState;
	
	private ResultType resultType = ResultType.EVALUATE;

	private boolean bagEvaluateFlag; //(false)0表示不是最终评， (true)1是最终评
	
	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="student_id")
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@Column(name="class_id")
	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	@Column(name="value")
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name="level_id")
	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	@Column(name="level_name")
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Enumerated
	@Column(name="evaluate_type")
	public EvaluateType getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(EvaluateType evaluateType) {
		this.evaluateType = evaluateType;
	}

	@Column(name="evaluate_id")
	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	@Column(name="prize")
	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	@Column(name="feedback")
	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Enumerated
	@Column(name="result_type")
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	@Column(name="bag_evaluate_flag")
	public boolean isBagEvaluateFlag() {
		return bagEvaluateFlag;
	}

	public void setBagEvaluateFlag(boolean bagEvaluateFlag) {
		this.bagEvaluateFlag = bagEvaluateFlag;
	}
	
	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
}
