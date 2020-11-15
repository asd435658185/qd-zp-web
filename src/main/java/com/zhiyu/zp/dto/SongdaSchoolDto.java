package com.zhiyu.zp.dto;

/**
 * 
 * @author wdj
 *
 */

public class SongdaSchoolDto
{

    public SongdaSchoolDto()
    {
    }

    public SongdaSchoolDto(String schoolId, String schoolName, String schoolCode, String provinceCode, String cityCode, String areaCode)
    {
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.schoolCode = schoolCode;
        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
    }

    public String getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(String schoolId)
    {
        this.schoolId = schoolId;
    }

    public String getSchoolName()
    {
        return schoolName;
    }

    public void setSchoolName(String schoolName)
    {
        this.schoolName = schoolName;
    }

    public String getSchoolCode()
    {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode)
    {
        this.schoolCode = schoolCode;
    }

    public String getProvinceCode()
    {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode)
    {
        this.provinceCode = provinceCode;
    }

    public String getCityCode()
    {
        return cityCode;
    }

    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }

    public String getAreaCode()
    {
        return areaCode;
    }

    public void setAreaCode(String areaCode)
    {
        this.areaCode = areaCode;
    }

    public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	private String schoolId;
    private String schoolName;
    private String schoolType;
    private String schoolCode;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
}
