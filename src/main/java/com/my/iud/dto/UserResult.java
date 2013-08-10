package com.my.iud.dto;

import com.taobao.api.domain.User;

public class UserResult extends BaseResult {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
