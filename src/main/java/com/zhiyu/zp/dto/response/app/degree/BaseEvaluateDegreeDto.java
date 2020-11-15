package com.zhiyu.zp.dto.response.app.degree;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhiyu.zp.enumcode.EvaluateMethod;

/**
 * 
 * @author wdj
 *
 */

public class BaseEvaluateDegreeDto {
	
	private String degreeName;

	private Long degreeId;
	
	private Integer maxNum = 1;
	
	//次评对应的维度评价结果
	private Map<EvaluateMethod, Integer> timeEvaluateResultMaps = Maps.newTreeMap();
	
	private Map<EvaluateMethod, Integer> operaterIds = Maps.newConcurrentMap();
	
	private List<Integer> everyTimeCountList = Lists.newArrayList();
	
	private Integer degreeEvaluateTotalNum = 0;//本级所获得所以星星数，最终数据
	
	private Integer currentTotalStartNum = 0;//本级所获得所以星星数，过程数据
	
	private List<BaseEvaluateDegreeDto> items = Lists.newArrayList();
	
	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Map<EvaluateMethod, Integer> getTimeEvaluateResultMaps() {
		return timeEvaluateResultMaps;
	}

	public void setTimeEvaluateResultMaps(Map<EvaluateMethod, Integer> timeEvaluateResultMaps) {
		this.timeEvaluateResultMaps = timeEvaluateResultMaps;
	}

	public Integer getDegreeEvaluateTotalNum() {
		return degreeEvaluateTotalNum;
	}

	public void setDegreeEvaluateTotalNum(Integer degreeEvaluateTotalNum) {
		this.degreeEvaluateTotalNum = degreeEvaluateTotalNum;
	}

	public List<BaseEvaluateDegreeDto> getItems() {
		return items;
	}

	public void setItems(List<BaseEvaluateDegreeDto> items) {
		this.items = items;
	}

	public List<Integer> getEveryTimeCountList() {
		return everyTimeCountList;
	}

	public void setEveryTimeCountList(List<Integer> everyTimeCountList) {
		this.everyTimeCountList = everyTimeCountList;
	}

	public Integer getCurrentTotalStartNum() {
		return currentTotalStartNum;
	}

	public void setCurrentTotalStartNum(Integer currentTotalStartNum) {
		this.currentTotalStartNum = currentTotalStartNum;
	}

	public Map<EvaluateMethod, Integer> getOperaterIds() {
		return operaterIds;
	}

	public void setOperaterIds(Map<EvaluateMethod, Integer> operaterIds) {
		this.operaterIds = operaterIds;
	}
}
