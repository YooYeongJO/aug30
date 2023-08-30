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
				<h1>메인영역</h1>
				<div class="box" style="background-color: #FAF4C0;" onclick="url('multiBoard')">
					게시판 관리
					<div id="comment">게시판을 관리합니다.</div>
				</div>
				<div class="box" style="background-color: #FFD8D8;" onclick="url('post')">
					게시글 관리
					<div id="comment">게시글을 관리합니다.</div>
				</div>
				<div class="box" style="background-color: #E4F7BA;" onclick="url('member')">
					회원 관리
					<div id="comment">회원을 관리합니다.</div>
				</div>
				<div class="box" style="background-color: #D9E5FF;" onclick="url('comment')">
					댓글 관리
					<div id="comment">댓글을 관리합니다.</div>
				</div>
				<div class="box" style="background-color: #E8D9FF;" onclick="url('mail')">
					메일 보내기
					<div id="comment">메일 보내기</div>
				</div>
				<div class="box" style="background-color: #FFD9FA;" onclick="url('notice')">
					공지사항
					<div id="comment">공지를 쓰고 관리합니다.</div>
				</div>
			</div>
		</div> 
	</div>
</body>
</html>