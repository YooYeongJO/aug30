package com.jose1593.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper	
public interface NoticeDAO {

	List<Map<String, Object>> list();

	Map<String, Object> detail(int nno);

	String getOriFileName(String fileName);

}
