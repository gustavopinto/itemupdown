package com.my.iud.dto;

import java.io.Serializable;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = -3889132352612797718L;

	private String userId;
	
	private String userName;
	
	private String code;
	
	private String accessToken;

	
	public UserDTO() {
		super();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
   	
}
