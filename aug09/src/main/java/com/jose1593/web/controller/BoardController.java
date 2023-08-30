package com.jose1593.web.controller;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jose1593.web.dto.BoardDTO;
import com.jose1593.web.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired // controller -> Service -> DAO
	private BoardService boardService;
	
	@GetMapping("/board")
	public String board(Model model) { 
		List<BoardDTO> list = boardService.boardList(); // boardService.boardList로 보내주기 1		
		// System.out.println(list);
		model.addAttribute("list", list); // board.jsp 글쓰기 밑에 ${list } 적고 값이 잘 오는지 확인해보기
		
		return "board";
	}
	
	@ResponseBody // AJAX 요청에 대한 응답으로 사용
	@PostMapping("/detail") // "/detail" 경로로 들어오는 POST 요청을 처리
	public String detail(@RequestParam("bno") int bno) {
		//  "bno"라는 요청 파라미터를 받아들이고, 그 값을 정수형 변수인 "bno"에 저장
		BoardDTO dto = boardService.detail(bno);
		
		// System.out.println(bno); // "bno" 값을 출력하고,
		// JSONObject json = new JSONObject();
		ObjectNode json = JsonNodeFactory.instance.objectNode();
				
		json.put("content", dto.getBcontent());
		json.put("uuid", dto.getUuid());
		json.put("ip", dto.getBip()); // postmapping에 detail
		
		//json.put("result", e); 
		System.out.println(json.toString());

		// 문자열 "{content : "본문내용입니다"}"을 응답으로 반환
		return json.toString();
	}
	
	@GetMapping("/write") // 글쓰기 
	public String write() {
		return "write";
	}
	
	@PostMapping("/write")
	public String write(HttpServletRequest request, HttpSession session) { // 요청을 처리하는 코드 작성
		// 로그인 한 사용자만 들어올 수 있습니다.
		// System.out.println(request.getParameter("title"));
		// System.out.println(request.getParameter("content"));
		
		BoardDTO dto = new BoardDTO(); // BoardDTo 생성하기
		dto.setBtitle(request.getParameter("title"));
		dto.setBcontent(request.getParameter("content"));
		// 세션에서 불러오겠습니다.
		dto.setM_id(String.valueOf(session.getAttribute("mid"))); // 세션에서 가져옴
		
		int result = boardService.write(dto);
		System.out.println(result);
		
		return "redirect:/board"; // board로 돌아가
	}
	
	@PostMapping("/delete") // 삭제하기
	public String delete(BoardDTO dto) {
		System.out.println(dto.getBno());
		System.out.println(dto.getUuid());
		
		
		return "redirect:/board";
	}
	
	@ResponseBody // AJAX 요청에 대한 응답으로 사용
	@PostMapping("/detail2")
	public String detail2(@RequestParam("bno") int bno) throws JsonProcessingException {
		BoardDTO detail = boardService.detail2(bno);
		// JSON에 담아서 내보내기
		
		ObjectMapper mapp = new ObjectMapper(); // jackson으로 불러오기
		String json = mapp.writeValueAsString(detail);
		System.out.println(json);
		
		
		return json;
		
	}

}
