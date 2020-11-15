package com.zhiyu.zp.dto.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.common.DegreeValuePair;

/**
 * 
 * @author wdj
 *
 */

public class AppEvaluateInfoCommitRequestDto extends AppEvaluateInfoDegreeRequestDto{

	private List<DegreeValuePair> items = Lists.newArrayList();

	public List<DegreeValuePair> getItems() {
		return items;
	}

	public void setItems(List<DegreeValuePair> items) {
		this.items = items;
	}

	
}
