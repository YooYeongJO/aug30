<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin || 멀티보드 관리하기</title>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<link rel="stylesheet" href="../css/admin.css"> <!-- 최상단 루트로 올라가서 admin.css까지-->
<link rel="stylesheet" href="../css/multiboard.css">
<style type="text/css">
.input-form{
	margin: 0 auto;
	margin-top: 10px;
	width: 90%;
	height: 30px;
	line-height: 30px;
	padding: 5px;
	background-color: white;
}
.input-form input, .input-form button{
	width: 24%;
	border: 0px;
	margin: 0;
	padding: 0;
	height: 20px;
	vertical-align: middle;
}
</style>

</head>
<body>
	<div class="container">
		<%@ include file="menu.jsp" %>
		
		<div class="main">
			<div class="article">
										
				<h1>메인영역</h1>
				
					<div class="div-table">
						<div class="div-row table-head">
							<div class="div-cell table-head">번호</div>
							<div class="div-cell table-head">카테고리</div>
							<div class="div-cell table-head">이름</div>
							<div class="div-cell table-head">url</div>
							<div class="div-cell table-head">참고</div>
						</div>
					<c:forEach items="${setupBoardList }" var="row">
					<div class="div-row">
						<div class="div-cell">${row.b_no }</div>
						<div class="div-cell">${row.mb_cate }</div>
						<div class="div-cell">${row.b_catename }</div>
						<div class="div-cell url">${row.b_url }</div>
						<div class="div-cell">${row.b_comment }</div>
					</div>
					</c:forEach>
					</div>
				<!-- 새로 입력하는 form입니다. -->
				<div class="input-form">
					<form action="./multiBoard" method="post">
						<input type="number" name="cateNum" required="required" placeholder="게시판 번호 입력">
						<input type="text" name="name" required="required" placeholder="게시판 이름 입력">
						<input type="text" name="comment" required="required" placeholder="참고를 남겨주세요" >
						<button type="submit">저장</button>
					</form>
					
					</div>
			</div>
		</div> 
	</div>
</body>
</html>