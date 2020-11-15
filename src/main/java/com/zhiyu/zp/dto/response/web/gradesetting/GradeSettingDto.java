package com.zhiyu.zp.dto.response.web.gradesetting;

/**
 * 
 * @author wdj
 *
 */

public class GradeSettingDto {

	private String gradeName;
	
	private String levelName;
	
	private int levelId;
	
	private Long gradeLevelId;//setting表的记录id
	
	private int gradeId;

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
