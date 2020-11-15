package com.zhiyu.zp.dto.response.web.gradesetting;

/**
 * 
 * @author wdj
 *
 */

public class UpdateSettingDto {

	private Long schoolId;
	
	private int levelId;
	
	private Long gradeLevelId;//setting表的记录id
	
	private int gradeId;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public Long getGradeLevelId() {
		return gradeLevelId;
	}

	public void setGradeLevelId(Long gradeLevelId) {
		this.gradeLevelId = gradeLevelId;
	}

	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	
	
}
