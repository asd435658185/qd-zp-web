package com.zhiyu.zp.dto;

import java.util.List;


/**
 * 
 * @author wdj
 *
 */

public class SchoolBagDto {
	private Integer schoolId;
	private String schoolName;
	private Integer schoolCurTermId;
	private Integer schoolBagId;
	private String schoolBagName;
	private List<SchoolBagDegreesDto> schoolBagDegreesDtos;
	public static class  SchoolBagDegreesDto{
		private String schoolDegreeName;
		private Integer schoolDegreeScore;
		private Integer schoolDegreeId;
		public String getSchoolDegreeName() {
			return schoolDegreeName;
		}
		public void setSchoolDegreeName(String schoolDegreeName) {
			this.schoolDegreeName = schoolDegreeName;
		}
		public Integer getSchoolDegreeScore() {
			return schoolDegreeScore;
		}
		public void setSchoolDegreeScore(Integer schoolDegreeScore) {
			this.schoolDegreeScore = schoolDegreeScore;
		}
		public Integer getSchoolDegreeId() {
			return schoolDegreeId;
		}
		public void setSchoolDegreeId(Integer schoolDegreeId) {
			this.schoolDegreeId = schoolDegreeId;
		}
    }
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Integer getSchoolCurTermId() {
		return schoolCurTermId;
	}
	public void setSchoolCurTermId(Integer schoolCurTermId) {
		this.schoolCurTermId = schoolCurTermId;
	}
	public Integer getSchoolBagId() {
		return schoolBagId;
	}
	public void setSchoolBagId(Integer schoolBagId) {
		this.schoolBagId = schoolBagId;
	}
	public String getSchoolBagName() {
		return schoolBagName;
	}
	public void setSchoolBagName(String schoolBagName) {
		this.schoolBagName = schoolBagName;
	}
	public List<SchoolBagDegreesDto> getSchoolBagDegreesDtos() {
		return schoolBagDegreesDtos;
	}
	public void setSchoolBagDegreesDtos(List<SchoolBagDegreesDto> schoolBagDegreesDtos) {
		this.schoolBagDegreesDtos = schoolBagDegreesDtos;
	}
}
