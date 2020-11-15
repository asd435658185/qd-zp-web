package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 综评学校设置
 * @author wdj
 *
 */
@Table(name="zp_school_setting")
@Entity
public class ZpSchoolSettingEntity extends BaseEntity{

	private Integer degreeWilling = 0;//0不愿意  1愿意
	
	private Long schoolId;
	
	private Integer degreeDept;

	@Column(name="degree_willing")
	public Integer isDegreeWilling() {
		return degreeWilling;
	}

	public void setDegreeWilling(Integer degreeWilling) {
		this.degreeWilling = degreeWilling;
	}

	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="degree_dept")
	public Integer getDegreeDept() {
		return degreeDept;
	}

	public void setDegreeDept(Integer degreeDept) {
		this.degreeDept = degreeDept;
	}
	
	
}
