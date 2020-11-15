package com.zhiyu.zp.dto.response.web.timesetting;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WeightUpdateDto extends WebBaseRequestDto{

	private Long degreeId;
	
	private String degreeName;
	
	private Long bagId;
	
	private List<BaseUpdateDto> items = Lists.newArrayList();

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public List<BaseUpdateDto> getItems() {
		return items;
	}

	public void setItems(List<BaseUpdateDto> items) {
		this.items = items;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}
	
	
}
