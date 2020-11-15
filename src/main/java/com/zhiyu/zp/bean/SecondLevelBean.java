package com.zhiyu.zp.bean;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class SecondLevelBean {

	private String name; 
	
	private String score;
	 
	private List<ThirdLevelBean> thirdLevelData = Lists.newArrayList();
	
	public SecondLevelBean(){}

	public SecondLevelBean(String name, String score) {
		super();
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<ThirdLevelBean> getThirdLevelData() {
		return thirdLevelData;
	}

	public void setThirdLevelData(List<ThirdLevelBean> thirdLevelData) {
		this.thirdLevelData = thirdLevelData;
	}
	
	
}
