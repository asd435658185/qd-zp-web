package com.zhiyu.zp.dto.response.app.report;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.dto.response.app.degree.EvaluateDegreeDto;

/**
 * 
 * @author wdj
 *
 */

public class EventualReportDto {

	private List<EvaluateDegreeDto> degreeInfos = Lists.newArrayList();;
	
	private Integer totalSecondValue;//也就是各大要素评价分值
	
	private String level;
	
	private String prize;
	
	private String feedback;

	public List<EvaluateDegreeDto> getDegreeInfos() {
		return degreeInfos;
	}

	public void setDegreeInfos(List<EvaluateDegreeDto> degreeInfos) {
		this.degreeInfos = degreeInfos;
	}

	public Integer getTotalSecondValue() {
		return totalSecondValue;
	}

	public void setTotalSecondValue(Integer totalSecondValue) {
		this.totalSecondValue = totalSecondValue;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPrize() {
		return prize;
	}

	public void setPrize(String prize) {
		this.prize = prize;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	
	
}
