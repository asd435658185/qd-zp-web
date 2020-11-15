package com.zhiyu.zp.dto.request;

import java.util.List;
import java.util.Map;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebBodyImportDto extends WebBaseRequestDto{

	private Integer markTypeValue = 2;
	
	private List<Map<String, Object>> data;

	public Integer getMarkTypeValue() {
		return markTypeValue;
	}

	public void setMarkTypeValue(Integer markTypeValue) {
		this.markTypeValue = markTypeValue;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
	
}
