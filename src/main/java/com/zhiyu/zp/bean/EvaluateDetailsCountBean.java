package com.zhiyu.zp.bean;

import java.util.Map;

import com.google.common.collect.Maps;
import com.zhiyu.zp.enumcode.EvaluateMethod;

/**
 * 评估明细的统计bean
 * @author wdj
 *
 */

public class EvaluateDetailsCountBean {

	private Map<EvaluateMethod, Integer> detailValueMap = Maps.newConcurrentMap();
	
	private Map<EvaluateMethod, Integer> detailIdMap = Maps.newConcurrentMap();
	
	private Integer totalMethodsValue = 0;

	public Map<EvaluateMethod, Integer> getDetailValueMap() {
		return detailValueMap;
	}

	public void setDetailValueMap(Map<EvaluateMethod, Integer> detailValueMap) {
		this.detailValueMap = detailValueMap;
	}

	public Integer getTotalMethodsValue() {
		return totalMethodsValue;
	}

	public void setTotalMethodsValue(Integer totalMethodsValue) {
		this.totalMethodsValue = totalMethodsValue;
	}

	public Map<EvaluateMethod, Integer> getDetailIdMap() {
		return detailIdMap;
	}

	public void setDetailIdMap(Map<EvaluateMethod, Integer> detailIdMap) {
		this.detailIdMap = detailIdMap;
	}
}
