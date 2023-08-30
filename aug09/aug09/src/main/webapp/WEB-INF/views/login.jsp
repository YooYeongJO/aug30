<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ❤ login</title>
    <link href="css/styles.css" rel="stylesheet" />
    <script src="./js/jquery-3.7.0.min.js"></script>
	<style type="text/css">
		.login-form{
			margin: 0 auto;
			padding: 10px;
			width: 400px;
			height: auto;
			background-color: rgba(240, 255, 240, 0.3);
		}
	</style>
	<script>
	//자바스크립트일때
		function loginCheck() {
			let id = document.querySelector("#id");//창입니다
			let pw = document.querySelector("#pw");//input창 입니다
			let checkItems = [ id, pw ];
			//alert(id + " / " + pw + " / " + checkItems);
			
			
			
			
			
			let flag = checkItems.every(function(item) {
				if (item.value === null || item.value === "") {
			      //alert(item.parentNode.parentNode.childNodes[1].innerHTML + "를 다시 입력해주세요");
				  //alert(item.parentNode.parentNode.querySelector("label").innerHTML + "를 다시 입력해주세요");
				    alert(item.parentNode.previousElementSibling.innerHTML + "를 다시 입력해주세요");

					item.focus();
				}
				return item.value !== ""; // 비어있으면 거짓,
			});

			if (flag == true) { // flag가 모두 참이어야 (하나라도 거짓이면 실행되지 않는다)
// 				alert("로그인합니다");
				//writeForm.submit();
				//가상 form만들기
				let form = document.createElement("form");
	            form.setAttribute("action", "./login.seki");
	            form.setAttribute("method", "post");
	            
	            let idField = document.createElement("input");
	            idField.setAttribute("type", "hidden");
	            idField.setAttribute("name", "id");
	            idField.setAttribute("value", id.value);
	            form.appendChild(idField);
	            
	            let pwField = document.createElement("input");
	            pwField.setAttribute("type", "hidden");
	            pwField.setAttribute("name", "pw");
	            pwField.setAttribute("value", pw.value);
	            form.appendChild(pwField);
	            
	            document.body.appendChild(form);
	            form.submit();         
			}
		}
	</script>
	
	<script type="text/javascript">
		$(function() { // jQuery 사용하기
			// 쿠키 값 가져오기
			let userID = getCookie("userID"); // 아이디
			let setCookieY = getCookie("setCookie"); // Y값
			
			// 쿠키 검사 -> 쿠키가 있다면 해당 쿠키에서 id값 가져와 id칸에 붙여넣기
			if(setCookieY == 'Y') {// 위에서 뽑아온 setCookie 값이 Y야??
				$("#saveID").prop("checked", true);
				$("#ID").val(userID);
			} else {
				$("#saveID").prop("checked", true);
			}
			
			
			$("#login").click(function(){ // #login을 클릭했을 때 ID하고 비밀번호 검사해
				
				// 아이디 패스워드 검사하기
				let id = $("#id").val(); // 사용자가 입력한 id, pw값을 가져올 수 있다
				let pw = $("#pw").val();
				
				if(id == '' || id.lenth < 4) { // 지금 가져온 id가 비어있거나 길이가 4 미만이야??
					 alert("올바른 아이디를 입력하세요");
					$("#id").focus();
					return false; // 멈춰
				}
				if(pw == '' || pw.lenth < 5) { // 지금 가져온 암호가 비어있거나 길이가 5 미만이야??
					 alert("올바른 암호를 입력하세요");
					$("#pw").focus();
					return false; // 멈춰
				}
				
				if($("#saveID").is(":checked")){ // 아이디저장 버튼이 체크되어 있다면
					// alert("체크되어있습니다.");
					// setCookie("userID", 사용자가입력한ID, 7(기간) );  
					setCookie("userID", id, 7);
					setCookie("setCookie", "Y", 7);
				} else {
					// alert("체크 x");
					// deleteCookie(); => 삭제 쿠키 호출
					deleteCookie("userID");
					deleteCookie("setCookie");
				}
				let form = document.createElement("form");
	            form.setAttribute("action", "./login.seki");
	            form.setAttribute("method", "post");
	            
	            let idField = document.createElement("input");
	            idField.setAttribute("type", "hidden");
	            idField.setAttribute("name", "id");
	            idField.setAttribute("value", id);
	            form.appendChild(idField);
	            
	            let pwField = document.createElement("input");
	            pwField.setAttribute("type", "hidden");
	            pwField.setAttribute("name", "pw");
	            pwField.setAttribute("value", pw);
	            form.appendChild(pwField);
	            
	            document.body.appendChild(form);
	            form.submit();
			}); 			
		});
		
		// setCookie()
		function setCookie(cookieName, value, exdays){ // userID, 사용자가입력한ID, 유지기간
			let exdate = new Date();
			exdate.setDate(exdate.getDate() + exdays); // 기존 날짜(exdate) + 일 수(exdays)
			
			let cookieValue = value + ((exdays == null ? "" : ";expires="+ exdate.toGMTString()));
			//											기간 없을때;          기간 있을때
			document.cookie = cookieName+"="+cookieValue;
			//                userID=jose1593; expires=2023-08-30
		}
		
		
		// deleteCookie() => cookieName이 들어오면 해당 내용을 삭제
		function deleteCookie(cookieName) {
			let expireDate = new Date(); // 만료 날짜
			expireDate.setDate(expireDate.getDate() - 1); // 만료날짜를 어제날짜로 변경해서 자동 삭제되게
			document.cookie = cookieName+"= "+";expires="+expireDate.toGMTString();
		}
		
		
		// getCookie() => 쿠키 내용 가져오기
		function getCookie(cookieName){
			let x, y;
			let val = document.cookie.split(";"); 
			// val 변수에는 현재 페이지에서 사용 가능한 모든 쿠키 정보가 
			// 세미콜론을 기준으로 분리되어 배열 형태로 저장
			for(let i = 0; i < val.length; i++){
				x = val[i].substr(0, val[i].indexOf("="));
				y = val[i].substr(val[i].indexOf("=") + 1);
				// x = x.replace(/^\s+|\s+$/g, ''); => 공백제거
				x = x.trim();
				if(x == cookieName) {
					return y;
				}
				
			}
		}
		
		
	</script>
</head>
<body>
<%@ include file="menu.jsp" %>
        <header class="masthead">
            <div class="container">
               <div class="rounded-3 login-form">
               		<h2>LOGIN</h2>
               		<img alt="login" src="./img/login.png" width="250px;">
				<c:if test="${param.error eq 'login' }">
				<div class="mb-3 row">
					<h2 style="color:white;">로그인 하세요.</h2>	
				</div>
				</c:if>
					<div class="mb-3 row">
						<label for="staticEmail" class="col-sm-3 col-form-label">I D</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="id" placeholder="아이디를 입력하세요">
						</div>
					</div>
				<div class="mb-3 row">
					<label for="inputPassword" class="col-sm-3 col-form-label">Password</label>
					<div class="col-sm-8">
						<input type="password" class="form-control" id="pw" placeholder="암호를 입력하세요">
					</div>
				</div>
				<div class="mb-3 row">
					<div class="col-sm-12">
						<input type="checkbox" id="saveID">
						<label for="saveID">아이디 저장</label>
					</div>
				</div>	
				<div class="mb-3 row">
					<div class="col-sm-12">
						<input type="button" id="login" class="btn btn-primary" value="login"> <!-- onclick="loginCheck()"> -->
						<input type="button" id="join" class="btn btn-info" value="가입하기">
					</div>
				</div>
               </div>

            </div>
        </header>
        
     <!-- login 에러가 들어오면 동작하게 하겠다 (에러가 있을때만 동작함)-->
     <c:if test="${param.error ne null }">
    	<script type="text/javascript">
     		alert("로그인 해야 사용할 수 있는 메뉴입니다.")
     	</script>
     </c:if>
     
        

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>