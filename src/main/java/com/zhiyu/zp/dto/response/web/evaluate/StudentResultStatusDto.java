package com.zhiyu.zp.dto.response.web.evaluate;

import java.util.List;
import com.zhiyu.baseplatform.entity.StudentBaseInfoEntity;

/**
 * 
 * @author wdj
 *
 */

public class StudentResultStatusDto {
	   private Integer classSelfSum;
	   private Integer classParentSum;
	   private Integer classTeacherSum;
	   private List<ClassStudentDto> classStudentList;
	   public static class ClassStudentDto{
		   private StudentBaseInfoEntity studentInfo;
		   private Boolean selfStatus;
	       private Boolean parentStatus;
	       private Boolean teacherStatus;
			public StudentBaseInfoEntity getStudentInfo() {
				return studentInfo;
			}
			public void setStudentInfo(StudentBaseInfoEntity studentInfo) {
				this.studentInfo = studentInfo;
			}
			public Boolean getSelfStatus() {
				return selfStatus;
			}
			public void setSelfStatus(Boolean selfStatus) {
				this.selfStatus = selfStatus;
			}
			public Boolean getParentStatus() {
				return parentStatus;
			}
			public void setParentStatus(Boolean parentStatus) {
				this.parentStatus = parentStatus;
			}
			public Boolean getTeacherStatus() {
				return teacherStatus;
			}
			public void setTeacherStatus(Boolean teacherStatus) {
				this.teacherStatus = teacherStatus;
			}
	   }
		public List<ClassStudentDto> getClassStudentList() {
			return classStudentList;
		}
		public void setClassStudentList(List<ClassStudentDto> classStudentList) {
			this.classStudentList = classStudentList;
		}
		public Integer getClassSelfSum() {
			return classSelfSum;
		}
		public void setClassSelfSum(Integer classSelfSum) {
			this.classSelfSum = classSelfSum;
		}
		public Integer getClassParentSum() {
			return classParentSum;
		}
		public void setClassParentSum(Integer classParentSum) {
			this.classParentSum = classParentSum;
		}
		public Integer getClassTeacherSum() {
			return classTeacherSum;
		}
		public void setClassTeacherSum(Integer classTeacherSum) {
			this.classTeacherSum = classTeacherSum;
		}
}
