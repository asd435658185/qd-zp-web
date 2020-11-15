package com.zhiyu.zp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 维度的图片信息
 * @author wdj
 *
 */
@Table(name="zp_evaluate_degree_pic_info")
@Entity
public class ZpEvaluateDegreePicInfoEntity extends BaseEntity{

	private String picUrl;
	
	private String picColor;

	@Column(name="pic_url")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Column(name="pic_color")
	public String getPicColor() {
		return picColor;
	}

	public void setPicColor(String picColor) {
		this.picColor = picColor;
	}
	
	
}
