package com.my.iud.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.iud.dto.UserParameter;
import com.my.iud.dto.UserResult;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;

@Repository("userRepository")
public class UserRepository {
	
	@Autowired
	private TaobaoClient taoBaoClient;
	
	public UserResult getUserLocation(UserParameter userParameter) throws Exception{
		UserSellerGetRequest req=new UserSellerGetRequest();	
		req.setFields("location.city,location.state");
		UserSellerGetResponse response = taoBaoClient.execute(req , userParameter.getSessionKey());
		
		UserResult userResult = new UserResult();
		userResult.setSucess(response.isSuccess());
		userResult.setErrorCode(response.getErrorCode());
		userResult.setMsg(response.getMsg());
		userResult.setSubCode(response.getSubCode());
		userResult.setSubMsg(response.getSubMsg());
		userResult.setUser(response.getUser());
		
		return userResult;
	}

}
