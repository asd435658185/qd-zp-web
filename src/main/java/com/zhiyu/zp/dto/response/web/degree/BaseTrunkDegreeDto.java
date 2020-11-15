package com.zhiyu.zp.dto.response.web.degree;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 维度支干节点
 * @author wdj
 *
 */

public class BaseTrunkDegreeDto extends BaseDegreeUpdateInfoDto{

	private List<BaseDegreeUpdateInfoDto> items = Lists.newArrayList();

	public List<BaseDegreeUpdateInfoDto> getItems() {
		return items;
	}

	public void setItems(List<BaseDegreeUpdateInfoDto> items) {
		this.items = items;
	}

}
