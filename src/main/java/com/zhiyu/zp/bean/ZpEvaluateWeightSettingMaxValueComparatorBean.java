package com.zhiyu.zp.bean;

import java.util.Comparator;

import com.zhiyu.zp.entity.ZpEvaluateWeightSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpEvaluateWeightSettingMaxValueComparatorBean implements Comparator<ZpEvaluateWeightSettingEntity>{

	public int compare(ZpEvaluateWeightSettingEntity o1, ZpEvaluateWeightSettingEntity o2) {
		return o1.getMaxValue()-o2.getMaxValue();
	}

	
}
