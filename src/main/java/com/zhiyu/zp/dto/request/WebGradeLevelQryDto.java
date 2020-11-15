package com.zhiyu.zp.dto.request;

import com.zhiyu.zp.dto.common.WebBaseRequestDto;

/**
 * 
 * @author wdj
 *
 */

public class WebGradeLevelQryDto extends WebBaseRequestDto{

	private int levelId;
	
	private String grades;

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}
	
	
}
