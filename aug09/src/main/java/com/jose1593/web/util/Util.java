package com.jose1593.web.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

 

//Controller Service Repository 
//Component = 객체

@Component
public class Util {
	
	

	// 문자열이 들어오면 숫자로 변경하기
	public int strToInt(String str) {
		// 숫자로 바꿀 수 있는 경우 숫자로, 만약 숫자로 못 바꾼다면?
		// "156" -> 156 "156번" -> 156
		int result = 0;

		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			//String re = "";// 숫자인것만 모을 스트링입니다.
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < str.length(); i++) {
				if (Character.isDigit(str.charAt(i))) {// 숫자인지 물어봄
					//re += str.charAt(i);// 숫자만 모아서
					sb.append(str.charAt(i));
				}
			}
			result = Integer.parseInt(sb.toString());// 숫자로 만들어서
		}
		return result;// 되돌려줍니다.
	}

	public String exchange(String str) {
		str = str.replaceAll("<", "&lt;");// 수정해주세요 제발
		str = str.replaceAll(">", "&gt;");// 수정해주세요 제발
		return str;
	}

	public String getIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		// 상대방 ip가져오기 2023-07-19
		String ip = null; // 192.168.0.0 -> HttpServletRequest가 있어야 합니다.
		ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	//숫자인지 확인하기
	public boolean isNum(Object obj) {
		try {
			Integer.parseInt(String.valueOf(obj));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//AdminController 에서 Util AutoWired 해주고
	public int obj2Int(Object obj) {
		return Integer.parseInt(String.valueOf(obj));
	}
	
	// 경로 얻어오기
	public HttpServletRequest getCurrentRequest() {
	      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	public HttpServletResponse getCurrentResponse() {
	      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
	}
	
	// 세션 얻어오기 = import => ctrl + shift + o
	public HttpSession getSession() {
	      return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
	
	// 업로드 폴더까지의 경로 얻어오기
	public String uploadPath() {
	      return getCurrentRequest().getServletContext().getRealPath("/upload");
	}
	
	public boolean simpleMailSender(Map<String, Object> map) throws EmailException {
			//메일 정보
				String emailAddr = "jose15931593@outlook.com"; // 보내는 사람 메일 주소
				String passwd = "dbxod1593!@"; // 메일의 암호를 넣어주세요.
				String name = "유영조"; // 보내는 사람 이름
				String hostname = "smtp.office365.com"; // SMTP 주소
				int port = 587; // 포트가 뭔지 모르면 검색하셔야 합니다.
				
				// 메일보내기 작업하기
				SimpleEmail mail = new SimpleEmail();
				mail.setCharset("UTF-8"); // 언어설정
				mail.setDebug(true); // 메일을 발송했을 때 어떻게 날라가는지 문제는 없는지 콘솔창에 뜬다
				mail.setHostName(hostname); // 고정
				mail.setAuthentication(emailAddr, passwd); // 인증 (고정)
				mail.setSmtpPort(port); // 포트번호 (고정)
				mail.setStartTLSEnabled(true); // 인증을 사용하는거니까 (고정)
				mail.setFrom(emailAddr, name); // 이메일주소와 name 값이 들어가유 (고정)
				
				mail.addTo((String) map.get("to")); // 받는 사람 (mail.jsp => input 창에 있는 to)
				mail.setSubject((String) map.get("title")); // 메일 제목 (mail.jsp => input 창에 있는 title)
				mail.setMsg((String) map.get("content")); // 본문 내용 (mail.jsp textarea에 있는 content)
				
				
				String result = mail.send(); //메일 보내기	
				System.out.println("메일 보내기 : " + result);
				return true;
		
	}
	
	public void htmlsimpleMailSender(Map<String, Object> map) throws EmailException {
		//메일 정보
			String emailAddr = "jose15931593@outlook.com"; // 보내는 사람 메일 주소
			String passwd = "dbxod1593!@"; // 메일의 암호를 넣어주세요.
			String name = "유영조"; // 보내는 사람 이름
			String hostname = "smtp.office365.com"; // SMTP 주소
			int port = 587; // 포트가 뭔지 모르면 검색하셔야 합니다.
			
			// 메일보내기 작업하기
			// SimpleEmail mail = new SimpleEmail();
			HtmlEmail mail = new HtmlEmail(); // html메일 보내기로 변경합니다.
			mail.setCharset("UTF-8"); // 언어설정
			mail.setDebug(true); // 메일을 발송했을 때 어떻게 날라가는지 문제는 없는지 콘솔창에 뜬다
			mail.setHostName(hostname); // 고정
			mail.setAuthentication(emailAddr, passwd); // 인증 (고정)
			mail.setSmtpPort(port); // 포트번호 (고정)
			mail.setStartTLSEnabled(true); // 인증을 사용하는거니까 (고정)
			mail.setFrom(emailAddr, name); // 이메일주소와 name 값이 들어가유 (고정)
			
			mail.addTo((String) map.get("to")); // 받는 사람 (mail.jsp => input 창에 있는 to)
			mail.setSubject((String) map.get("title")); // 메일 제목 (mail.jsp => input 창에 있는 title)
			//mail.setMsg((String) map.get("content")); // 본문 내용 (mail.jsp textarea에 있는 content)
			
			// 이미지 경로 잡아오기
			String path = uploadPath();
			String img = "http://172.30.1.41/img/IMG_0472.jpg";
			
			String file2 = path + "/202308221552097886c5e1-83f0-417d-8519-e4a318267a80123.xml";
			
			String html = "<html>"; // 코드가 길어지니 여기서 작업해서 넣어줄게욤.
			html += "<h1>그림을 첨부합니다</h1>";
			html += "<img alt=\"이미지\" src='"+img+"'>";
			html += "<h2>임시 비밀번호를 보내드립니다.</h2>";
			html += "<div> 임시 암호 : password486</div>";
			html += "<h3>아래 링크를 클릭해서 암호를 변경해주세요.</h3>";
			html += "<a href=http=\"http://nid.naver.com\">눌러주세요</a>";
			html += "</html>";
			mail.setHtmlMsg(html);
			// 첨부파일도 보내기
			EmailAttachment file = new EmailAttachment();
			// 위 파일은 문서파일입니다.			
			file.setPath(file2);
			mail.attach(file);
			
			String result = mail.send(); //메일 보내기	
			System.out.println("메일 보내기 : " + result);
			
		
	
}
}
