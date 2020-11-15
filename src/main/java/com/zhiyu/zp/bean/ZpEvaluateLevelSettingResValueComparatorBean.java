package com.zhiyu.zp.bean;

import java.util.Comparator;

import com.zhiyu.zp.entity.ZpLevelSettingEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpEvaluateLevelSettingResValueComparatorBean implements Comparator<ZpLevelSettingEntity>{

	public int compare(ZpLevelSettingEntity o1, ZpLevelSettingEntity o2) {
		return o1.getEvaluateLevelType().getValue()-o2.getEvaluateLevelType().getValue();
	}

	
}
