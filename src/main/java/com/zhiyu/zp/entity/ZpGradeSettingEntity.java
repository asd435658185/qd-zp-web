package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.GradeLevel;

/**
 * 综评年级设置
 * @author wdj
 *
 */
@Table(name="zp_grade_setting")
@Entity
public class ZpGradeSettingEntity extends BaseEntity{

	private Long schoolId;
	
	private GradeLevel levelId;
	
	private Long gradeId;
	
	private DataState dataState;
	
	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Enumerated
	@Column(name="level_id")
	public GradeLevel getLevelId() {
		return levelId;
	}

	public void setLevelId(GradeLevel levelId) {
		this.levelId = levelId;
	}

	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	@Column(name="grade_id")
	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	
	
}
