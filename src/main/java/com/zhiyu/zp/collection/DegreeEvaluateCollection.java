package com.zhiyu.zp.collection;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */
@Document(collection="degree")
public class DegreeEvaluateCollection implements Serializable{

	private static final long serialVersionUID = -8193289091505169309L;

	@Id
	private String id;
	
	private List<?> degreeInfos = Lists.newArrayList();;
	
	private Integer totalSecondValue;//也就是各大要素评价分值
	
	private String level;
	
	private String prize;
	
	private String feedback;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<?> getDegreeInfos() {
		return degreeInfos;
	}

	public void setDegreeInfos(List<?> degreeInfos) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
