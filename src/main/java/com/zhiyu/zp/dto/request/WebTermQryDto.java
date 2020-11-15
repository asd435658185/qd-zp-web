package com.zhiyu.zp.dto.request;

import java.util.Date;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebTermQryDto extends WebBaseRequestDto{

	private Date nextRegDate;
	
	private Date nextStartDate;
	
	private Long id;

	public Date getNextRegDate() {
		return nextRegDate;
	}

	public void setNextRegDate(Date nextRegDate) {
		this.nextRegDate = nextRegDate;
	}

	public Date getNextStartDate() {
		return nextStartDate;
	}

	public void setNextStartDate(Date nextStartDate) {
		this.nextStartDate = nextStartDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
