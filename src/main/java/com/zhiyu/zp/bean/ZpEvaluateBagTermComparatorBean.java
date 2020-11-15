package com.zhiyu.zp.bean;

import java.util.Comparator;

import com.zhiyu.zp.entity.ZpEvaluateBagEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpEvaluateBagTermComparatorBean implements Comparator<ZpEvaluateBagEntity>{

	public int compare(ZpEvaluateBagEntity o1, ZpEvaluateBagEntity o2) {
		return o1.getTermId().intValue()-o2.getTermId().intValue();
	}

}
