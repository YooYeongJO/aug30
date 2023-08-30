package com.jose1593.web.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper // mapper는 interface로 적어줘야 한다
public interface LoginDAO {

	public Map<String, Object> login(Map<String, String> map);

	public Map<String, Object> myInfo(String id);
		
	
	}


