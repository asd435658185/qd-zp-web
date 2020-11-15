package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.Useable;

/**
 * 评估报告册
 * @author wdj
 *
 */
@Table(name="zp_evaluate_bag")
@Entity
public class ZpEvaluateBagEntity extends BaseEntity{

	private Long termId;
	
	private String bagName;
	
	private Long schoolId;
	
	private Useable useable;
	
	private DataState dataState;
	
	private Integer versionId;
	
	private String topDegrees;
	
	private String gradeIds;
	
	@Enumerated
	@Column(name="useable")
	public Useable getUseable() {
		return useable;
	}

	public void setUseable(Useable useable) {
		this.useable = useable;
	}

	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}
	
	@Column(name="version_id")
	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	@Column(name="term_id")
	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	@Column(name="bag_name")
	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="top_degrees")
	public String getTopDegrees() {
		return topDegrees;
	}

	public void setTopDegrees(String topDegrees) {
		this.topDegrees = topDegrees;
	}

	@Column(name="grade_ids")
	public String getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}
	
}
