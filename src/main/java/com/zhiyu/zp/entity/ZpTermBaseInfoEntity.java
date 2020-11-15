package com.zhiyu.zp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author wdj
 *
 */
@Table(name="zp_term_base_info")
@Entity
public class ZpTermBaseInfoEntity extends BaseEntity{

	private Long termId;
	
	private Date nextRegDate;
	
	private Date nextStartDate;

	@Column(name="term_id")
	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	@Column(name="next_reg_date")
	public Date getNextRegDate() {
		return nextRegDate;
	}

	public void setNextRegDate(Date nextRegDate) {
		this.nextRegDate = nextRegDate;
	}

	@Column(name="next_start_date")
	public Date getNextStartDate() {
		return nextStartDate;
	}

	public void setNextStartDate(Date nextStartDate) {
		this.nextStartDate = nextStartDate;
	}
	
	
}
