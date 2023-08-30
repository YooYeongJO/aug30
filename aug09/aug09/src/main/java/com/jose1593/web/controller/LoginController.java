package com.jose1593.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jose1593.web.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	//2023-08-11 프레임워크 프로그래밍
	@GetMapping("/login.seki")
	
	public String index() {
		return "login";
	}
	
	// 2023-08-16 프레임워크 프로그래밍
	@PostMapping("/login.seki")
	public String login(@RequestParam Map<String, String> map, HttpSession session) {
		// 세션이 있다면 다른 곳으로 이동
		// id / pw 값이 없다면 다른 곳으로 이동
		// System.out.println(map);
		Map<String, Object> result = loginService.login(map); 
		// result를 꺼내서 0(정상적으로 로그인 되지 않음), 1 (정상적으로 로그인 됨)  
		// System.out.println(result);
		// {m_name=짱아, count=1}
		
		if(String.valueOf(result.get("count")).equals("1")) {  
			// 정상로그인이라면 세션 만들고, index로 이동합니다.
			session.setAttribute("mid", map.get("id"));
			session.setAttribute("mname", result.get("m_name"));	
			return "redirect:/";
		} else {
			// 다시 로그인으로 가기
			return "login";
		}
	}
	
	@GetMapping("/logout.seki")
	public String logout(HttpSession session) {
		if(session.getAttribute("mid") != null ) { // null이 아니라면
			session.removeAttribute("mid");
		}
		if(session.getAttribute("mname") != null) { // null이 아니라면
			session.removeAttribute("mname");
		}
		// 다른
		session.invalidate();
		
		return "redirect:/"; // index로 보내기
	}
	
	// 2023-08-18 요구사항 확인
	// @PathVariable 사용법 => 경로가 바뀌어도 변수 값이 들어온다
	@GetMapping("/myInfo@{id}") // myInfo 뒤에 아이디가 나온다.
	public ModelAndView myInfo(@PathVariable("id") String id, HttpSession session) { 
 
		System.out.println("jsp가 보내준 값 : " + id);
		System.out.println(id.equals(session.getAttribute("mid"))); // 참 거짓으로 나온다.
		// 이 아이디가 session.getAttribute와 일치하는지
		
		// 회원가입할때 개인정보 수정할때 암호 암호화하기
		Map<String, Object> myInfo = loginService.myInfo(id);
		ModelAndView mv = new ModelAndView(); // 객체 선언 = jsp명이 없는 상태 
		// modelAndView 사용할때 괄호안에 jsp 이름 적어줘야 한다.
		mv.setViewName("myInfo"); // 이동할 jsp 파일명
		mv.addObject("my", myInfo); // 값 붙이기
		return mv;
	}
	
}
