package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="zp_mark_subject")
@Entity
public class ZpMarkSubjectEntity extends BaseEntity {

	private Long schoolId;
	
	private String engCode;
	
	private String subjectName;
	
	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="eng_code")
	public String getEngCode() {
		return engCode;
	}

	public void setEngCode(String engCode) {
		this.engCode = engCode;
	}

	@Column(name="subject_name")
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

}
