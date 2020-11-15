
package com.zhiyu.zp.dto;


public class SongdaSchoolClassDto
{

    public SongdaSchoolClassDto()
    {
    }

    public String getClassId()
    {
        return classId;
    }

    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getGradeId()
    {
        return gradeId;
    }

    public void setGradeId(String gradeId)
    {
        this.gradeId = gradeId;
    }

    public String getClassCode()
    {
        return classCode;
    }

    public void setClassCode(String classCode)
    {
        this.classCode = classCode;
    }

    public String getClassNickName()
    {
        return classNickName;
    }

    public void setClassNickName(String classNickName)
    {
        this.classNickName = classNickName;
    }

    public String getGradeCode()
    {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode)
    {
        this.gradeCode = gradeCode;
    }

    public String getGradeName()
    {
        return gradeName;
    }

    public void setGradeName(String gradeName)
    {
        this.gradeName = gradeName;
    }

    public String getMasterId()
    {
        return masterId;
    }

    public void setMasterId(String masterId)
    {
        this.masterId = masterId;
    }
    public String getStudyYear() {
		return studyYear;
	}

	public void setStudyYear(String studyYear) {
		this.studyYear = studyYear;
	}
    private String classId;
    private String className;
    private String classCode;
    private String classNickName;
    private String gradeId;
    private String gradeCode;
    private String gradeName;
    private String masterId;
    private String studyYear;//学年
    
}


