package com.zhiyu.zp.dto.response.web.mark;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhiyu.zp.enumcode.MarkType;

/**
 * 成绩结果dto
 * @author wdj
 *
 */

public class MarkResultContentDto {

	private String studentNo;
	
	private Long studentId;
	
	private String studentName;
	
	private String className;
	
	private String gradeName;
	
	private List<Mark> marks = Lists.newArrayList();
	
	public static class Mark{
		
		private String name;
		
		private String markTypeName;
		
		private MarkType markType;
		
		private List<String> scores = Lists.newArrayList();

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

		public List<String> getScores() {
			return scores;
		}

		public void setScores(List<String> scores) {
			this.scores = scores;
		}

		public MarkType getMarkType() {
			return markType;
		}

		public void setMarkType(MarkType markType) {
			this.markType = markType;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	
}
