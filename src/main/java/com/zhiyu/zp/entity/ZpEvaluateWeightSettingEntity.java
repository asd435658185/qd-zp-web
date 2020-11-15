package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.EvaluateType;

/**
 * 评估权重设置
 * @author wdj
 *
 */
@Table(name="zp_evaluate_weight_setting")
@Entity
public class ZpEvaluateWeightSettingEntity extends BaseEntity{

	private Long evaluateBagId;
	
	private EvaluateType evaluateType;
	
	private Long degreeId;
	
	private Integer maxValue;
	
	private Integer minValue;
	
	private DataState dataState;
	
	private DegreeType degreeType;
	
	private Integer resValue;
	
	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	@Column(name="evaluate_bag_id")
	public Long getEvaluateBagId() {
		return evaluateBagId;
	}

	public void setEvaluateBagId(Long evaluateBagId) {
		this.evaluateBagId = evaluateBagId;
	}

	@Enumerated
	@Column(name="evaluate_type")
	public EvaluateType getEvaluateType() {
		return evaluateType;
	}

	public void setEvaluateType(EvaluateType evaluateType) {
		this.evaluateType = evaluateType;
	}

	@Column(name="degree_id")
	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
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
	@Column(name="degree_type")
	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	@Column(name="res_value")
	public Integer getResValue() {
		return resValue;
	}

	public void setResValue(Integer resValue) {
		this.resValue = resValue;
	}

	
}
