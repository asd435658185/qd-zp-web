package com.zhiyu.zp.bean;

import java.util.Comparator;

import com.zhiyu.zp.entity.ZpMarkResultEntity;

/**
 * 
 * @author wdj
 *
 */

public class ZpMarkResultEntitySubjectIdComparatorBean implements Comparator<ZpMarkResultEntity>{

	public int compare(ZpMarkResultEntity o1, ZpMarkResultEntity o2) {
		return o1.getSubjectId().intValue()-o2.getSubjectId().intValue();
	}

	
}
