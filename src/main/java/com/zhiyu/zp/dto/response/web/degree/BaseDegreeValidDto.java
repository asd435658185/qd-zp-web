package com.zhiyu.zp.dto.response.web.degree;

/**
 * 
 * @author wdj
 *
 */

public class BaseDegreeValidDto extends BaseDegreeDescDto{

	private String useableName;
	
	private int useableId = 0;

	public String getUseableName() {
		return useableName;
	}

	public void setUseableName(String useableName) {
		this.useableName = useableName;
	}

	public int getUseableId() {
		return useableId;
	}

	public void setUseableId(int useableId) {
		this.useableId = useableId;
	}
	
	
}
