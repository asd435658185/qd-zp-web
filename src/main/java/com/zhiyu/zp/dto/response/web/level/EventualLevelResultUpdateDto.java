package com.zhiyu.zp.dto.response.web.level;

import java.util.List;

/**
 * 
 * @author wdj
 *
 */

public class EventualLevelResultUpdateDto {

	private Long schoolId;
	
	private List<EventualLevelResultDto> rows;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public List<EventualLevelResultDto> getRows() {
		return rows;
	}

	public void setRows(List<EventualLevelResultDto> rows) {
		this.rows = rows;
	}
	
	
	
}
