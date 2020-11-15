package com.zhiyu.zp.dto;

/**
 * 
 * @author wdj
 *
 */

public class OrgBaseInfoDto {
	 public OrgBaseInfoDto()
	    {
	    }

	    public OrgBaseInfoDto(String orgId,String bureauCode, String bureauName, 
	    		String provinceCode,String cityCode,String areaCode,String orgType)
	    {
	    	this.orgId=orgId;
	        this.bureauCode = bureauCode;
	        this.bureauName = bureauName;
	        this.provinceCode=provinceCode;
	        this.cityCode=cityCode;
	        this.areaCode=areaCode;
	        this.orgType = orgType;
	    }

	    private String orgId;
	    private String bureauCode;
	    private String bureauName;
	    private String provinceCode;
	    private String cityCode;
	    private String areaCode;
	    private String orgType;//机构类型1表示教育局2表示中心校
		public String getOrgId() {
			return orgId;
		}

		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}

		public String getBureauCode() {
			return bureauCode;
		}

		public void setBureauCode(String bureauCode) {
			this.bureauCode = bureauCode;
		}

		public String getBureauName() {
			return bureauName;
		}

		public void setBureauName(String bureauName) {
			this.bureauName = bureauName;
		}

		public String getProvinceCode() {
			return provinceCode;
		}

		public void setProvinceCode(String provinceCode) {
			this.provinceCode = provinceCode;
		}

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getAreaCode() {
			return areaCode;
		}

		public void setAreaCode(String areaCode) {
			this.areaCode = areaCode;
		}

		public String getOrgType() {
			return orgType;
		}

		public void setOrgType(String orgType) {
			this.orgType = orgType;
		}
}
