<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ❤ 글쓰기</title>
	<!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
    <script src="./js/jquery-3.7.0.min.js"></script>
	<style type="text/css">
#main{
	margin: 0 auto;
	width:95%;
	margin-top: 60px;
	color: black;
}
#detail{
	background-color: white;
	margin: 0 auto;
	width: 90%;
	height: auto;
	margin-top: 15px;
	color: black;
	padding: 10px;
	box-sizing: border-box;
}
#detailH{
	height: 40px;
	line-height: 40px;
	font-size: x-large;
	border-bottom: 1px solid black;
	box-sizing: border-box;
	overflow: hidden;
}
#detailIdDate{
	height: 50px;
	line-height: 30px;
	background-color: #D3D3D3;
	border-bottom: 1px gray solid;
	padding:10px;
	box-sizing: border-box;
}
#detailID, #detailDate{
	width: 45%;
	float: left;
	text-align: left;
}
#detailDate{
	float: right;
	text-align: right;
}
#detailContent{
	padding:10px;
	min-height:300px;
	height: auto;
	border-bottom: 1px solid white;
	box-sizing: border-box;
	text-align: left;
}
</style>
</head>
<body>
<%@ include file="menu.jsp" %>
 <!-- Masthead-->
        <header class="masthead">
            <div class="container">
               <div id="detail">
               		<div id="detailH">${detail.ntitle }</div>
               		<div id="detailN_no" style="height: 0px; visibility: hidden;">${detail.nno }</div>
               		<div id="detailIdDate">
               			<div id="detailID">${detail.m_no }</div>
						<div id="detailDate">${detail.ndate }</div>
					</div>
               		<div id="detailContent">${detail.ncontent }</div>  
               		<div class="file-form">           		
               		<c:forTokens var="token" items="${detail.nrealfile}" delims="." varStatus="status">
                		<c:if test="${status.last }"> <!-- .을 기준으로 잘라주는데 맨 마지막꺼 -->
                			<c:choose>
                				<c:when test="${token eq 'PNG' || token eq 'jpg' 
                				|| token eq 'jpeg' || token eq 'bmp' || token eq 'gif' }"> <!-- 이미지 파일이야?? -->
                					<img alt="" src="../upload/${detail.nrealfile}"> 
                				</c:when>
                				<c:otherwise> <!-- 이미지 파일이 아니야?? -->
                					<button onclick="location.href='./download@${detail.nrealfile}'">다운로드</button>
                				</c:otherwise>
                			
                			</c:choose>
                		</c:if>
                		
                	</c:forTokens>
               		</div>
					             			               		
               <button class="btn btn-primary xi-view-list" onclick="location.href='./multiboard'">멀티보드</button>
            </div>
            </div>
        </header>

        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <!-- * *                               SB Forms JS                               * *-->
        <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
</body>
</html>