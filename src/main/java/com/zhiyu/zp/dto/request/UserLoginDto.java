package com.zhiyu.zp.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author wdj
 *
 */

public class UserLoginDto {

	@NotBlank
	private String userAccount;
	
	@NotBlank
	private String password;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
