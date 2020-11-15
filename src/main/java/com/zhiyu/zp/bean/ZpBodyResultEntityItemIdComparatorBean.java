package com.zhiyu.zp.bean;

import java.util.Comparator;

import com.zhiyu.zp.entity.ZpBodyResultEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpBodyResultEntityItemIdComparatorBean implements Comparator<ZpBodyResultEntity>{

	public int compare(ZpBodyResultEntity o1, ZpBodyResultEntity o2) {
		return o1.getItemId().intValue()-o2.getItemId().intValue();
	}

	
}
