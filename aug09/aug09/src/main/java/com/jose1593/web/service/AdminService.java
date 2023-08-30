package com.jose1593.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jose1593.web.dao.AdminDAO;

@Service
public class AdminService {
	
	@Autowired
	private AdminDAO adminDAO;
	
	

	public Map<String, Object> adminLogin(Map<String, Object> map) {
		
		return adminDAO.adminLogin(map);
	}


	public List<Map<String, Object>> list() {
		return adminDAO.list();
	}


	public void noticeWrite(Map<String, Object> map) {
		adminDAO.noticeWrite(map);
		
	}


	public String noticeDetail(int nno) {
		
		return adminDAO.noticeDetail(nno);
	}


	public int noticeHide(int nno) {
		return adminDAO.noticeHide(nno); // 해당 글을 0으로 바꿔주는 녀석
		
	}


	public List<Map<String, Object>> setupBoardList() {
		
		return adminDAO.setupBoardList();
	}


	public int multiBoardInsert(Map<String, String> map) {
		
		return adminDAO.multiBoardInsert(map);
	}


	public List<Map<String, Object>> memberList() {
		
		return adminDAO.memberList();
	}


	public int gradeChange(Map<String, String> map) {
		
		return adminDAO.gradeChange(map);
	}


	public List<Map<String, Object>> post(Map<String, Object> map) {
		
		return adminDAO.post(map);
	}


	public List<Map<String, Object>> boardList() {
		
		return adminDAO.boardList();
	}


	public String content(int mbno) {
		
		return adminDAO.content(mbno);
	}



}
