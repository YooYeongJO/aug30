package com.jose1593.web.controller;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jose1593.web.service.AdminService;
import com.jose1593.web.util.Util;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;


@Controller
@RequestMapping("/admin") // admin 밑으로 들어오는 uri는 다 여기로 들어온다.
// admin/admin 이렇게 적어줬지만 RequestMapping으로 써주면 
// /admin, /login 이런 형태로 적어준다.
public class AdminController {
	// AdminService / AdminDAO / AdminMapper (count(id pw 일치하는가), 등급, *)
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private Util util;
		
	
	@GetMapping("/")
	public String adminIndex2() {
		return "forward:/admin/admin"; // url 경로명을 유지하고 화면내용만 갱신합니다.
		// redirect는 컨트롤러를 다시 돌려서 해당 jsp로 이동 (redirect:/main)이면 main으로 이동
		// forward는 url 경로명을 유지하고 화면내용만 갱신한다.
	}

	@GetMapping("/admin") // URI 경로가 "/" 또는 "/admin"인 요청을 처리하는 메서드를 지정
	public String adminIndex() {
		return "admin/index";  // admin/index"라는 뷰를 반환,  admin 폴더 밑에 index.jsp가 있다.
	}
	
	@PostMapping("/login") // 사용자가 입력한 값을 받아서 내보내 줍니다.
	public String adminLogin(@RequestParam Map<String, Object> map, HttpSession session) { // id, 등급, 
		System.out.println(map); // 값이 오는지 출력문으로 확인
		Map<String, Object> result = adminService.adminLogin(map);
		System.out.println(result);
		// {m_grade=9, m_name=야, count=1}
		// System.out.println(String.valueOf(result.get("count")).equals("1"));
		// System.out.println(Integer.parseInt(String.valueOf(result.get("m_grade"))) > 5);
		
		if(util.obj2Int(result.get("count")) == 1 && util.obj2Int(result.get("m_grade")) > 5) {
			// System.out.println("집에 가고 싶어 베이붸");
			// 세션 올리기
			session.setAttribute("mid", map.get("id"));
			session.setAttribute("mname", result.get("m_name"));
			session.setAttribute("mgrade", result.get("m_grade"));
			// 메인으로 이동하기
			return "redirect:/admin/main";
		} else { // 잘못 됐으면 로그인 창으로 가게
			return "redirect:/admin/admin?error=login";
			
		}
	}
	
	@GetMapping("/main")
	public String main() { // 페이지만 전환시키고 끝남
		return "admin/main"; //jsp경로라 폴더 적어줘야 admin/밑에 main.jsp를 열어줍니다. 
	}
	
	@GetMapping("/notice")
	public String notice(Model model) { // DB랑 연결해야 되고, notice.jsp를 찍어주는게 필요하다
		// 1데이터베이스까지 연결하기
		List<Map<String, Object>> list = adminService.list();
		//3 데이터 jsp로 보내기
		model.addAttribute("list", list);
		return "admin/notice"; //jsp경로라 폴더 적어줘야 admin/밑에 notice.jsp를 열어줍니다
	}
	
	@PostMapping("/noticeWrite") // Map으로 잡는 방법
	public String noticeWrite(@RequestParam("upFile") MultipartFile upfile, @RequestParam Map<String, Object> map) { 
		// upfile이 map에 들어가기도 한다.
		// System.out.println(map);
		
		
		// 2023-08-22 요구사항확인
		// 
		if(!upfile.isEmpty()) { // upfile이 들어왔다면
			
			// 저장할 경로명 뽑기 request 뽑기
			// noticeWrite에 request 값이 없어서 먼저 값을 주고 가야된다
			HttpServletRequest request = 
			((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest(); 
			// 내 실제경로를 뽑아주는 명령
			String path = request.getServletContext().getRealPath("/upload"); 
			// 파일은 upload 여기로 저장된다
			// System.out.println("실제 경로" + path);
			
			
			// upfile 정보보기
			// System.out.println(upfile.getOriginalFilename()); // 실제 파일이름 가져오기
			// System.out.println(upfile.getSize()); // 파일의 용량크기
			// System.out.println(upfile.getContentType()); // 무슨형태의 파일을 업로드 하는거야
			// 진짜로 파일 업로드 하기 경로 + 저장할 파일명

			// 실제 경로C:\Users/user\git\repository9\aug09/src\main/webapp/upload\1593.PNG
			// String 타입의 경로를 file형태로 바꿔주겠습니다.
			// File filePath = new File(path);
			
			// 파일은 중복이 발생할 수 있기때문에..... (파일명 + 날짜 + ID .파일확장자)
			//											UUID + 파일명 + .확장자
			//											UUID + 파일명 + 아이디 + .확장자
			
			//UUID 값 뽑기
			
			// 날짜 뽑기 SimpleDateFormat
			// Date date = new Date();
			// SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
			// String dateTime = sdf.format(date);
			
			UUID uuid = UUID.randomUUID();
			// String realFileName = uuid.toString() + upfile.getOriginalFilename();
			// 다른 날짜 뽑기 형식 (LocalDateTime)
			LocalDateTime ldt = LocalDateTime.now();
			String format = ldt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss")); // 현재 날짜 시간
			String realFileName = format + uuid.toString() + upfile.getOriginalFilename();
			//              		날짜  +   UUID          +  실제 파일명으로 사용하겠습니다.
						
			File newFileName = new File(path, realFileName); // File 객체 생성해주기
			// 이제 파일 올립니다.
			
			
			
			try {
				// upfile.transferTo(newFileName); // newFileName 경로에 파일명으로 저장해
				// upfile => MultipartFile 객체의 변수명 
			} catch (Exception e) { // Exception으로 변경해줘야 위에 upfile문을 주석처리가 가능하다
				e.printStackTrace();
			}  
			// System.out.println("저장 끝.");
			
			// FileCopyUtils를 사용하기 위해서는 오리지널 파일을 byte[]로 만들어야 합니다.
			try {
				FileCopyUtils.copy(upfile.getBytes(), newFileName); 
				// 실제 사용자가 올려주는 파일을, 뒤쪽에 있는 newFileName 경로로 올려준다.
			} catch (IOException e) {	
				e.printStackTrace();
			}
			
			// #{upFile}, #{realFile }
			map.put("upFile", upfile.getOriginalFilename());
			map.put("realFile", realFileName); // newFileName 값을 넣으면 경로까지 같이 저장된다.
			
		}
		
		
		map.put("mno", 1); // 로그인한 사람의 아이디를 담아주세요세요
		adminService.noticeWrite(map);
		return "redirect:/admin/notice";
	}
	
	@GetMapping("/mail")
	public String mail() {
		return "/admin/mail";
	}
	
	@PostMapping("mail")
	public String mail(@RequestParam Map<String, Object> map) throws EmailException {
			
		//util.simpleMailSender(map);
		util.htmlsimpleMailSender(map);
		return "admin/mail";
	}
	
	// noticeDetail
	@ResponseBody
	@PostMapping("/noticeDetail")
	public String noticeDetail(@RequestParam("nno") int nno) {
		System.out.println(nno);
		
		//jackson 사용해보기
		ObjectNode json = JsonNodeFactory.instance.objectNode(); // 객체 생성
		// json.put("name", "홍길동"); 
		/* 1. 데이터 베이스에 물어보기 -> nno로 -> 본문내용 가져오기
		 * 2. jackson에 담아주세요.
		 */
//		Map<String, Object> maaap = new HashMap<String, Object>();
//		maaap.put("bno", 123);
//		maaap.put("btitle", 1234);
//		
//		ObjectMapper jsonMap = new ObjectMapper();
//			
//		try {
//			json.put("map", jsonMap.writeValueAsString(maaap));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		json.put("content", adminService.noticeDetail(nno)); // String, JsonNode 값만 들어올 수 있다. 
		
		return json.toString();  
}
	
	// noticeHide
	@ResponseBody // 자체적으로 값 받아가려면
	@PostMapping("/noticeHide") // Hide 보여주기 감추기 하나로 
	public String noticeHide(@RequestParam("nno") int nno) { 
		int result = adminService.noticeHide(nno);
		ObjectNode json = JsonNodeFactory.instance.objectNode();
		json.put("result", result);
		return json.toString(); 
	}
	
	// 2023-08-24 어플리케이션 테스트 수행
	@RequestMapping(value = "/multiBoard", method = RequestMethod.GET) // get방식으로 받을게요 
	// value 값만 들어있을 때는 get방식과 post방식 둘다 받겠다는 의미이다.
	public String multiBoard(Model model) {
		// setup보드 내용을 가져와서 아래 jsp에 찍어주세유.
		List<Map<String, Object>> setupBoardList = adminService.setupBoardList();
		model.addAttribute("setupBoardList", setupBoardList);
		return "admin/multiBoard";
	}
	
	
	// multiboard 2023-08-25 어플리케이션 테스트 수행
	@RequestMapping(value = "/multiBoard", method = RequestMethod.POST) // post방식으로 받을게요 
	// value 값만 들어있을 때는 get방식과 post방식 둘다 받겠다는 의미이다.
	public String multiBoard(@RequestParam Map<String, String> map) {
		//DB에 저장하기
		
		int result = adminService.multiBoardInsert(map);
		System.out.println("result : " + result);
		return "redirect:/admin/multiBoard";
	}
	
	
	//member (전체 회원관리 만들기 - 비밀번호 제외)
	@RequestMapping(value = "/member", method = RequestMethod.GET) // get방식으로 받을게요 
	// value 값만 들어있을 때는 get방식과 post방식 둘다 받겠다는 의미이다.
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView("admin/member"); // 적어줄 페이지명
		mv.addObject("memberList", adminService.memberList());
		return mv;
	}
	
	// gradeChange
	@RequestMapping(value = "/gradeChange", method = RequestMethod.GET) // get방식으로 받을게요 
	// value 값만 들어있을 때는 get방식과 post방식 둘다 받겠다는 의미이다.
	public String gradeChange(@RequestParam Map<String, String>  map) {
		int result = adminService.gradeChange(map);
		System.out.println(result);
		return "redirect:/admin/member";
	}
	
	@GetMapping("/post")
	public String post(Model model, @RequestParam(name="cate", required = false, defaultValue = "0") int cate, 
			@RequestParam Map<String, Object> map) {
		// 게시판 번호가 들어올 수 있습니다. 추후 처리
		// 게시판 관리번호를 다 불러옵니다.
		
		if(!(map.containsKey("cate")) || map.get("cate").equals(null) || map.get("cate").equals("")) { 
			// map에서 cate라는 녀석이 없거나 map에서 cate라는 녀석이 비어있어?? 
			map.put("cate", 0);
		}
		System.out.println("cate : " + cate);
		System.out.println("검색 : " + map);
		
		List<Map<String, Object>> boardList = adminService.boardList(); // board 없이 싹다 읽어올 수 있는 녀석
		model.addAttribute("boardList", boardList);
		
		// 게시글을 다 불러옵니다.
		List<Map<String, Object>> list = adminService.post(map); // board 없이 싹다 읽어올 수 있는 녀석
		model.addAttribute("list", list);
		System.out.println(list.get(0).get("count"));
		return "/admin/post";
	}
	
	@ResponseBody
	@GetMapping("/detail")
	public String detail(@RequestParam("mbno") int mbno) {
		// mbno : mbno
		String content = adminService.content(mbno);
		System.out.println(content);
		JSONObject json = new JSONObject();
		json.put("content", content);
		return json.toString();
	}
	
	@GetMapping("/corona")
	public String corona(Model model) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1790387/covid19CurrentStatusKorea/covid19CurrentStatusKoreaJason"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=F6JXyeatHxaFUgIUoAVecT003hs2VhqGazwev8BzGQWVYeiL%2FixeAuSqmrDeeZsZytkqLlCJV2DKdurqso7IOA%3D%3D"); /*Service Key*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        // System.out.println(sb.toString());
        model.addAttribute("corona", sb.toString());
        
        
        // String to Json (String을 Json으로 바꾸는 방법)
        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode jsonN = objectMapper.readTree(sb.toString());
        // JsonNode jsonV = objectMapper.readValue(sb.toString(), JsonNode.class);
        System.out.println(jsonN.get("response").get("result").get(0));
        // response 속 result 0번지에 있는 것을 출력해
        
        // Json to map (Json 값만 있을때)
		Map<String, Object> result = objectMapper.readValue(
				(jsonN.get("response").get("result").get(0)).toString()
				, new TypeReference<Map<String, Object>>(){}
				);
        
		System.out.println(result);
		model.addAttribute("result", result);
		
		return "/admin/corona";
	}
	
	
	@GetMapping("/air2")
	public String air2() throws Exception {
//		 StringBuilder urlBuilder = new StringBuilder(
//				 "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getMsrstnAcctoRDyrg"); /*URL*/
//	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=F6JXyeatHxaFUgIUoAVecT003hs2VhqGazwev8BzGQWVYeiL%2FixeAuSqmrDeeZsZytkqLlCJV2DKdurqso7IOA%3D%3D"); /*Service Key*/
//	        urlBuilder.append("&returnType=xml"); /*xml 또는 json*/
//	        urlBuilder.append("&numOfRows=100"); /*한 페이지 결과 수*/
//	        urlBuilder.append("&pageNo=1"); /*페이지번호*/
//	        urlBuilder.append("&inqBginDt=20230801"); /*조회시작일자*/
//	        urlBuilder.append("&inqEndDt=20230829"); /*조회종료일자*/
//	        urlBuilder.append("&msrstnName=" + URLEncoder.encode("강남구", "UTF-8")); /*측정소명*/
//	        URL url = new URL(urlBuilder.toString());
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("GET");
//	        conn.setRequestProperty("Content-type", "application/json");
//	        System.out.println("Response code: " + conn.getResponseCode());
//	        BufferedReader rd;
//	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//	        } else {
//	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//	        }
//	        StringBuilder sb = new StringBuilder();
//	        String line;
//	        while ((line = rd.readLine()) != null) {
//	            sb.append(line);
//	        }
//	        rd.close();
//	        conn.disconnect();
//	        System.out.println(sb.toString());
//		
//	     // String to xml (String을 xml로 바꾸는 방법)
//	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//	        DocumentBuilder builder = factory.newDocumentBuilder();
//	        Document document = builder.parse(new InputSource(new StringReader(sb.toString())));
//	        // Document로 만들 것을 문서형식으로
//	        
//	        // document.getDocumentElement().normalize();
//	        System.out.println(document);
		return "";
	}
	
	@GetMapping("/air")
	   public String air(Model model) throws Exception {
	      // String to xml
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      Document document = builder.parse("c:\\temp\\air.xml");

	      //document.getDocumentElement().normalize();
	      System.out.println(document.getDocumentElement().getNodeName());
	      
	      NodeList list = document.getElementsByTagName("item");
	         //System.out.println("item length = " + list.getLength());
	         //System.out.println(list.toString());
	         ArrayList<Map<String, Object>> coronaList = new ArrayList<Map<String,Object>>();
	         for (int i = list.getLength() - 1; i >= 0; i--) {
	            NodeList childList = list.item(i).getChildNodes(); 
	          //자식 노드를 뽑아서 childList로 map으로 만들어서 
	            Map<String, Object> value = new HashMap<String, Object>();
	            for (int j = 0; j < childList.getLength(); j++) {
	               Node node = childList.item(j);
	               if (node.getNodeType() == Node.ELEMENT_NODE) { 
	                  //System.out.println(node.getNodeName());
	                  //System.out.println(node.getTextContent());
	                  value.put(node.getNodeName(), node.getTextContent());
	               }
	            }
	            coronaList.add(value);
	         }
	         System.out.println("xml : " + coronaList);
	         model.addAttribute("list", coronaList);

	      return "/admin/air";
	   }
	
	
	
	
	
}