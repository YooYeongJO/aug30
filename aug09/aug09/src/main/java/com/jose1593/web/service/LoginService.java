package com.jose1593.web.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose1593.web.dao.LoginDAO;

@Service
public class LoginService {

	@Autowired
	private LoginDAO loginDAO;
	
	public Map<String, Object> login(Map<String, String> map) {
		
		return loginDAO.login(map);
	}

	public Map<String, Object> myInfo(String id) {
		
		return loginDAO.myInfo(id);
	}
	

}
