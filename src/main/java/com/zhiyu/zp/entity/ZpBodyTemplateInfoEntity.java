package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="zp_body_template_info")
@Entity
public class ZpBodyTemplateInfoEntity extends BaseEntity {

	private Long schoolId;
	
	private String items;
	
	private Long gradeId;

	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="grade_id")
	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	@Column(name="items")
	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	
}
