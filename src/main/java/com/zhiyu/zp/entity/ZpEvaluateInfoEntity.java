package com.zhiyu.zp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.Useable;

/**
 * 评估信息
 * @author wdj
 *
 */
@Table(name="zp_evaluate_info")
@Entity
public class ZpEvaluateInfoEntity extends BaseEntity{

	private Long evaluateBagId;
	
	private String evaluateName;
	
	private Useable useable;
	
	private DataState dataState;
	
	private DegreeType degreeType;
	
	private String degrees;
	
	private Date beginDate;
	
	private Date endDate;
	
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

	@Column(name="evaluate_bag_id")
	public Long getEvaluateBagId() {
		return evaluateBagId;
	}

	public void setEvaluateBagId(Long evaluateBagId) {
		this.evaluateBagId = evaluateBagId;
	}

	@Column(name="evaluate_name")
	public String getEvaluateName() {
		return evaluateName;
	}

	public void setEvaluateName(String evaluateName) {
		this.evaluateName = evaluateName;
	}

	@Enumerated
	@Column(name="degree_type")
	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	@Column(name="degrees")
	public String getDegrees() {
		return degrees;
	}

	public void setDegrees(String degrees) {
		this.degrees = degrees;
	}

	@Column(name="begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name="end_date")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
}
