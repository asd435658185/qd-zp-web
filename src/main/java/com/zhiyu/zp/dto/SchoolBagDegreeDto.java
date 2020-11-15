package com.zhiyu.zp.dto;

import java.util.List;

/**
 * 
 * @author wdj
 *
 */

public class SchoolBagDegreeDto {
	private Integer schoolId;
	private String schoolName;
	private Integer schoolCurTermId;
	private Integer schoolBagId;
	private String schoolBagName;
	private Integer schoolGradeId;
	private String schoolGradeName;
	private Integer schoolGradeClassId;
	private String schoolGradeClassName;
	private Integer schoolGradeClassStudentId;
	private List<StudentBagDegreeDto> studentBagDegreeDtos;
	public static class StudentBagDegreeDto{
		private String schoolDegreeName;
		private String schoolBagName;
		private Integer sbScore;
		private Integer sbNum;
		private Integer schoolDegreeId;
		private Integer schoolBagId;
        private Integer sbId;
		public String getSchoolDegreeName() {
			return schoolDegreeName;
		}
		public void setSchoolDegreeName(String schoolDegreeName) {
			this.schoolDegreeName = schoolDegreeName;
		}
		public String getSchoolBagName() {
			return schoolBagName;
		}
		public void setSchoolBagName(String schoolBagName) {
			this.schoolBagName = schoolBagName;
		}
		public Integer getSbScore() {
			return sbScore;
		}
		public void setSbScore(Integer sbScore) {
			this.sbScore = sbScore;
		}
		public Integer getSbNum() {
			return sbNum;
		}
		public void setSbNum(Integer sbNum) {
			this.sbNum = sbNum;
		}
		public Integer getSchoolDegreeId() {
			return schoolDegreeId;
		}
		public void setSchoolDegreeId(Integer schoolDegreeId) {
			this.schoolDegreeId = schoolDegreeId;
		}
		public Integer getSchoolBagId() {
			return schoolBagId;
		}
		public void setSchoolBagId(Integer schoolBagId) {
			this.schoolBagId = schoolBagId;
		}
		public Integer getSbId() {
			return sbId;
		}
		public void setSbId(Integer sbId) {
			this.sbId = sbId;
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
	public Integer getSchoolGradeId() {
		return schoolGradeId;
	}
	public void setSchoolGradeId(Integer schoolGradeId) {
		this.schoolGradeId = schoolGradeId;
	}
	public String getSchoolGradeName() {
		return schoolGradeName;
	}
	public void setSchoolGradeName(String schoolGradeName) {
		this.schoolGradeName = schoolGradeName;
	}
	public Integer getSchoolGradeClassId() {
		return schoolGradeClassId;
	}
	public void setSchoolGradeClassId(Integer schoolGradeClassId) {
		this.schoolGradeClassId = schoolGradeClassId;
	}
	public String getSchoolGradeClassName() {
		return schoolGradeClassName;
	}
	public void setSchoolGradeClassName(String schoolGradeClassName) {
		this.schoolGradeClassName = schoolGradeClassName;
	}
	public Integer getSchoolGradeClassStudentId() {
		return schoolGradeClassStudentId;
	}
	public void setSchoolGradeClassStudentId(Integer schoolGradeClassStudentId) {
		this.schoolGradeClassStudentId = schoolGradeClassStudentId;
	}
	public List<StudentBagDegreeDto> getStudentBagDegreeDtos() {
		return studentBagDegreeDtos;
	}
	public void setStudentBagDegreeDtos(List<StudentBagDegreeDto> studentBagDegreeDtos) {
		this.studentBagDegreeDtos = studentBagDegreeDtos;
	}
}
