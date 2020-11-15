package com.zhiyu.zp.dto.response.web.timesetting;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.common.WebBaseRequestDto;
import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public class WeightViewDto extends WebBaseRequestDto{

	private Long degreeId;
	
	private String degreeName;
	
	private Long bagId;
	
	private List<ZpEvaluateWeightSettingEntity> items = Lists.newArrayList();

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

	public List<ZpEvaluateWeightSettingEntity> getItems() {
		return items;
	}

	public void setItems(List<ZpEvaluateWeightSettingEntity> items) {
		this.items = items;
	}

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}
	
	
}
