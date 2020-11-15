package com.zhiyu.zp.dto.response.web.level;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class EventualLevelResultDto {

	private Long levelId;
	
	private String levelName;
	
	private List<LevelResult> items = Lists.newArrayList();
	
	public static class LevelResult{
		
		private Long id;
		
		private int minValue;
		
		private int maxValue;
		
		private Long evaluateLevelId;
		
		private String evaluateLevelName;
		
		private int status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public int getMinValue() {
			return minValue;
		}

		public void setMinValue(int minValue) {
			this.minValue = minValue;
		}

		public int getMaxValue() {
			return maxValue;
		}

		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}

		public Long getEvaluateLevelId() {
			return evaluateLevelId;
		}

		public void setEvaluateLevelId(Long evaluateLevelId) {
			this.evaluateLevelId = evaluateLevelId;
		}

		public String getEvaluateLevelName() {
			return evaluateLevelName;
		}

		public void setEvaluateLevelName(String evaluateLevelName) {
			this.evaluateLevelName = evaluateLevelName;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}
		
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public List<LevelResult> getItems() {
		return items;
	}

	public void setItems(List<LevelResult> items) {
		this.items = items;
	}
	
	
}
