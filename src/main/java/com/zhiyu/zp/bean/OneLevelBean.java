package com.zhiyu.zp.bean;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class OneLevelBean {

	private String name;
	
	private List<SecondLevelBean> secondLevelData = Lists.newArrayList();
	
	public OneLevelBean(){}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SecondLevelBean> getSecondLevelData() {
		return secondLevelData;
	}

	public void setSecondLevelData(List<SecondLevelBean> secondLevelData) {
		this.secondLevelData = secondLevelData;
	}

	
	
	
}
