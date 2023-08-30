package com.jose1593.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose1593.web.dao.NoticeDAO;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeDAO noticeDAO;

	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = noticeDAO.list();
		return list;
//		return noticeDAO.list();
	}

	public Map<String, Object> detail(int nno) {
		
		return noticeDAO.detail(nno);
	}

	public String getOriFileName(String fileName) {
		
		return noticeDAO.getOriFileName(fileName);
	}
	
	
	

}
