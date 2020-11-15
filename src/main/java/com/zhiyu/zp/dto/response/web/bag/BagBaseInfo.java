package com.zhiyu.zp.dto.response.web.bag;

/**
 * 
 * @author wdj
 *
 */

public class BagBaseInfo {

	private Long bagId;
	
	private String bagName;
	
	private String termName;
	
	private Long termId;
	
	private int useableId;
	
	private String useableName;
	
	private Integer versionId;
	
	private String versionName;
	
    private String topDegrees;
    
    private String topDegreeNames;
	
	private String gradeIds;
	
	private String gradeNames;

	public Long getBagId() {
		return bagId;
	}

	public void setBagId(Long bagId) {
		this.bagId = bagId;
	}

	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public int getUseableId() {
		return useableId;
	}

	public void setUseableId(int useableId) {
		this.useableId = useableId;
	}

	public String getUseableName() {
		return useableName;
	}

	public void setUseableName(String useableName) {
		this.useableName = useableName;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getTopDegrees() {
		return topDegrees;
	}

	public void setTopDegrees(String topDegrees) {
		this.topDegrees = topDegrees;
	}

	public String getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(String gradeIds) {
		this.gradeIds = gradeIds;
	}

	public String getTopDegreeNames() {
		return topDegreeNames;
	}

	public void setTopDegreeNames(String topDegreeNames) {
		this.topDegreeNames = topDegreeNames;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}
	
}
