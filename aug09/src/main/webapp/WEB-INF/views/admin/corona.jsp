<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || main</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css"> <!-- 최상단 루트로 올라가서 admin.css까지-->
</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		
		<div class="main">
			<div class="article">
				<h1>코로나 일일 내역</h1>
				<div>
					<table border="1">
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td class="mmddhh">mmddhh</td>
						</tr>
						<tr>	
							<td>일일 사망</td>
							<td class="cnt_deaths"cnt_deaths></td>
						</tr>
						<tr>	
							<td>일일 신규입원</td>
							<td class="cnt_hospitalizations">cnt_hospitalizations</td>
						</tr>
						<tr>	
							<td>일일 확진</td>
							<td class="cnt_confirmations">cnt_confirmations</td>
						</tr>
					</table>
					
					<table>
						<tr>
							<td>데이터 조회 기준 일시</td>
							<td>${result.mmddhh }</td>
						</tr>
						<tr>	
							<td>일일 사망</td>
							<td>${result.cnt_deaths }</td>
						</tr>
						<tr>	
							<td>일일 신규입원</td>
							<td>${result.cnt_hospitalizations }</td>
						</tr>
						<tr>	
							<td>일일 확진</td>
							<td>${result.cnt_confirmations }</td>
						</tr>
					</table>
				
				</div>
			</div>
		</div> 
	</div>
<script type="text/javascript">
	let corona = ${corona}; // ${corona} String타입인데 let corona가 ajax로 자동으로 변환해준다
	document.querySelector(".mmddhh").innerText = corona.response.result[0].mmddhh;
	// innerText - node 혹은 element의 텍스트 값을 읽어오고, 설정, 변경할 수 있다.
	// innerText - 사용자에게 보여지는 텍스트만을 읽어옴

	document.querySelector(".cnt_deaths").innerText = corona.response.result[0].cnt_deaths;
	// querySelector를 찾아서 .cnt_deaths" 이렇게 변경하겠습니다.
	// 해당 코드는 선택한 요소의 텍스트 내용을 코로나 데이터에서 가져온 사망자 수로 설정하는 역할을 합니다.
	
	document.querySelector(".cnt_hospitalizations").innerText = corona.response.result[0].cnt_hospitalizations;
	
	document.querySelector(".cnt_confirmations").innerText = corona.response.result[0].cnt_confirmations;
</script>	
	
	
</body>
</html>