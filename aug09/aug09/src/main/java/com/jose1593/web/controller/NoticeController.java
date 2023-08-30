package com.jose1593.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jose1593.web.service.NoticeService;
import com.jose1593.web.util.Util;

@Controller
public class NoticeController {
	
	// 필요한 거? model + map + service + DAO + Mapper
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private Util util;
	
	@GetMapping("/notice")
	public String notice(Model model) {
		List<Map<String, Object>> list = noticeService.list();
		model.addAttribute("list", list);
		System.out.println(list);
		
		return "notice"; // notice.jsp에 찍어주려면 Model이 필요하다
	}
	
	// 2023-08-22
	//noticeDetail
	@GetMapping("/noticeDetail")
	public String noticeDetail(@RequestParam("nno") int nno, Model model) {
		System.out.println(nno);
		Map<String, Object> detail = noticeService.detail(nno);
		model.addAttribute("detail", detail);
		return "noticeDetail";
	}
	
	//다운로드 처리하기 /download@파일명
	// 202308221552097886c5e1-83f0-417d-8519-e4a318267a80 123.xml
	@ResponseBody 
	@GetMapping("/download@{fileName}")
	public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) {
		System.out.println(fileName);
		String path = util.uploadPath();
		// C:\Users/user/git\repository9/aug09/src/main/webapp/upload
		// System.out.println(path);
		String oriFileName = noticeService.getOriFileName(fileName);

		File serverSideFile = new File(path, fileName);
		try {
			byte[] fileByte= FileCopyUtils.copyToByteArray(serverSideFile);
			response.setContentType("application/octet-stream"); 
			// 파일을 전송시킬 녀석, stream은 계속해서 파일을 내보내겠습니다.
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(oriFileName, "UTF-8")+"\";");
	        response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}	
}
