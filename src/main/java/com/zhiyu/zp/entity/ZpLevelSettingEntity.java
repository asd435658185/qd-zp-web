package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.EvaluateLevelType;

/**
 * 综评等级设置
 * @author wdj
 *
 */
@Table(name="zp_level_setting")
@Entity
public class ZpLevelSettingEntity extends BaseEntity{

	private Long levelId;
	
	private Integer maxValue;
	
	private Integer minValue;
	
	private Long schoolId;
	
	private EvaluateLevelType evaluateLevelType;

	@Column(name="level_id")
	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	
	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="max_value")
	public Integer getMaxValue() {
		return maxValue;
	}


	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	@Column(name="min_value")
	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	@Enumerated
	@Column(name="evaluate_level_id")
	public EvaluateLevelType getEvaluateLevelType() {
		return evaluateLevelType;
	}

	public void setEvaluateLevelType(EvaluateLevelType evaluateLevelType) {
		this.evaluateLevelType = evaluateLevelType;
	}
	
	
}
