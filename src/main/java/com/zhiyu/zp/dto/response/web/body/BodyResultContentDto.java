package com.zhiyu.zp.dto.response.web.body;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
 * @author wdj
 *
 */

public class BodyResultContentDto {

	private String studentNo;
	
	private String studentName;
	
	private String className;
	
	private String gradeName;
	
	private List<Mark> marks = Lists.newArrayList();
	
	public static class Mark{
		
		private String name;
		
		private String markTypeName;
		
		private List<String> values = Lists.newArrayList();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMarkTypeName() {
			return markTypeName;
		}

		public void setMarkTypeName(String markTypeName) {
			this.markTypeName = markTypeName;
		}

		public List<String> getValues() {
			return values;
		}

		public void setValues(List<String> values) {
			this.values = values;
		}

	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public List<Mark> getMarks() {
		return marks;
	}

	public void setMarks(List<Mark> marks) {
		this.marks = marks;
	}
	
	
}
