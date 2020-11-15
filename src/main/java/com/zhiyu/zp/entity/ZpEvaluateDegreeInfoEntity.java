package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.zhiyu.zp.enumcode.DataState;
import com.zhiyu.zp.enumcode.DegreeType;
import com.zhiyu.zp.enumcode.HasChildren;
import com.zhiyu.zp.enumcode.Useable;

/**
 * 维度信息
 * @author wdj
 *
 */
@Table(name="zp_evaluate_degree_info")
@Entity
public class ZpEvaluateDegreeInfoEntity extends BaseEntity{

	private Long pid;
	
	private String dname;
	
	private String dcode;
	
	private HasChildren hasChildren;//0没有  1有
	
	private String description;
	
	private Integer dsort;
	
	private Useable useable;
	
	private Long schoolId;
	
	private DegreeType degreeType;
	
	private Integer maxNum;
	
	private Long degreePicId;
	
	private DataState dataState;
	
	private Integer versionId;

	@Column(name="pid")
	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Column(name="dname")
	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Column(name="dcode")
	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	@Enumerated
	@Column(name="has_children")
	public HasChildren getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(HasChildren hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	@Column(name="dsort")
	public Integer getDsort() {
		return dsort;
	}

	public void setDsort(Integer dsort) {
		this.dsort = dsort;
	}

	@Enumerated
	@Column(name="useable")
	public Useable getUseable() {
		return useable;
	}

	public void setUseable(Useable useable) {
		this.useable = useable;
	}

	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Enumerated
	@Column(name="degree_type")
	public DegreeType getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(DegreeType degreeType) {
		this.degreeType = degreeType;
	}

	@Column(name="max_num")
	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	@Column(name="degree_pic_id")
	public Long getDegreePicId() {
		return degreePicId;
	}

	public void setDegreePicId(Long degreePicId) {
		this.degreePicId = degreePicId;
	}

	@Enumerated
	@Column(name="data_state")
	public DataState getDataState() {
		return dataState;
	}

	public void setDataState(DataState dataState) {
		this.dataState = dataState;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="version_id")
	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	
}
