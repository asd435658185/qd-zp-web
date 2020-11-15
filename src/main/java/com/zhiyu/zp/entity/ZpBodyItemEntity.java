package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="zp_body_item")
@Entity
public class ZpBodyItemEntity extends BaseEntity {

	private Long schoolId;
	
	private String engCode;
	
	private String itemName;
	
	@Column(name="school_id")
	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	@Column(name="eng_code")
	public String getEngCode() {
		return engCode;
	}

	public void setEngCode(String engCode) {
		this.engCode = engCode;
	}

	@Column(name="item_name")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
