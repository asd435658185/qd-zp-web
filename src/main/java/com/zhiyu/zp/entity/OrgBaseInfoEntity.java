package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="org_base_info")
@Entity
public class OrgBaseInfoEntity extends BaseEntity {
    private String bureauCode;
    private String bureauName;
    private String orgType;
    
    @Column(name="bureau_code")
	public String getBureauCode() {
		return bureauCode;
	}
	public void setBureauCode(String bureauCode) {
		this.bureauCode = bureauCode;
	}
	
	@Column(name="bureau_name")
	public String getBureauName() {
		return bureauName;
	}
	public void setBureauName(String bureauName) {
		this.bureauName = bureauName;
	}
	
	@Column(name="org_type")
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
    
}
