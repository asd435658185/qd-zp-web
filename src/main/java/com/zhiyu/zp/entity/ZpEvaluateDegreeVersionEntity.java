package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 维度信息
 * @author wdj
 *
 */
@Table(name="zp_evaluate_degree_version")
@Entity
public class ZpEvaluateDegreeVersionEntity extends BaseEntity{
	private String versionName;
	private Integer schoolId;
	
	@Column(name="version_name")
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	@Column(name="school_id")
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	
}
