package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.EvaluateMethod;

/**
 * 评价明细
 * @author wdj
 *
 */
@Table(name="zp_evaluate_detail")
@Entity
public class ZpEvaluateDetailEntity extends BaseEntity{

	private Long resultId;
	
	private Integer value;
	
	private Long degreeId;
	
	private DataState dataState;
	
	private Long operaterId;
	
	private EvaluateMethod evaluateMethod;

	@Column(name="result_id")
	public Long getResultId() {
		return resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	@Column(name="value")
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name="degree_id")
	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	@Column(name="operater_id")
	public Long getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(Long operaterId) {
		this.operaterId = operaterId;
	}

	@Enumerated
	@Column(name="eveluate_method")
	public EvaluateMethod getEvaluateMethod() {
		return evaluateMethod;
	}

	public void setEvaluateMethod(EvaluateMethod evaluateMethod) {
		this.evaluateMethod = evaluateMethod;
	}
	
	
	
}
