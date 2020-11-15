package com.zhiyu.zp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="zp_realistic")
@Entity
public class ZpRealisticEntity extends BaseEntity{
   
	private Integer termId;
	private Integer classId;
	private Integer studentId;
	private Integer evaluateId;
	private String realisticType;
	private String realisticName;
	private String realisticDec;
	private Date realisticDate;
	private String realisticScore;
	private String realisticUrl;
	private Integer realisticNum;
	private String realisticFileName;
	private Integer realisticUserId;
	private Integer realisticUserType;
	
	@Column(name="term_id")
	public Integer getTermId() {
		return termId;
	}
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	
	@Column(name="class_id")
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	
	@Column(name="student_id")
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	@Column(name="evaluate_id")
	public Integer getEvaluateId() {
		return evaluateId;
	}
	public void setEvaluateId(Integer evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	@Column(name="realistic_type")
	public String getRealisticType() {
		return realisticType;
	}
	public void setRealisticType(String realisticType) {
		this.realisticType = realisticType;
	}
	
	@Column(name="realistic_name")
	public String getRealisticName() {
		return realisticName;
	}
	public void setRealisticName(String realisticName) {
		this.realisticName = realisticName;
	}
	
	@Column(name="realistic_dec")
	public String getRealisticDec() {
		return realisticDec;
	}
	public void setRealisticDec(String realisticDec) {
		this.realisticDec = realisticDec;
	}
	
	@Column(name="realistic_date")
	public Date getRealisticDate() {
		return realisticDate;
	}
	public void setRealisticDate(Date realisticDate) {
		this.realisticDate = realisticDate;
	}
	
	@Column(name="realistic_score")
	public String getRealisticScore() {
		return realisticScore;
	}
	public void setRealisticScore(String realisticScore) {
		this.realisticScore = realisticScore;
	}
	
	@Column(name="realistic_url")
	public String getRealisticUrl() {
		return realisticUrl;
	}
	public void setRealisticUrl(String realisticUrl) {
		this.realisticUrl = realisticUrl;
	}
	@Column(name="realistic_num")
	public Integer getRealisticNum() {
		return realisticNum;
	}
	public void setRealisticNum(Integer realisticNum) {
		this.realisticNum = realisticNum;
	}
	@Column(name="realistic_file_name")
	public String getRealisticFileName() {
		return realisticFileName;
	}
	public void setRealisticFileName(String realisticFileName) {
		this.realisticFileName = realisticFileName;
	}
	
	@Column(name="realistic_user_id")
	public Integer getRealisticUserId() {
		return realisticUserId;
	}
	public void setRealisticUserId(Integer realisticUserId) {
		this.realisticUserId = realisticUserId;
	}
	
	@Column(name="realistic_user_type")
	public Integer getRealisticUserType() {
		return realisticUserType;
	}
	public void setRealisticUserType(Integer realisticUserType) {
		this.realisticUserType = realisticUserType;
	}
}
