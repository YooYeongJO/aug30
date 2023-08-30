<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> ❤ board</title>
	<!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/styles.css" rel="stylesheet" />
    <script src="./js/jquery-3.7.0.min.js"></script> <!-- 제이쿼리 -->
    <style type="text/css">
		.title{
			text-align: left;
		}
		.detail-detail{
			width: 100%;
			height: auto;
		}
		.detail-name, .detail-date-read{
			width: 100%;
			height: 30px;
			border-bottom: 1px solid #c0c0c0;
		}
		.detail-date-read{
			background-color: silver;
		}
		.detail-date{
			padding-left:10px;
			float: left;
		}
		.detail-read{
			padding-right:10px;
			float: right;
		}
		.detail-content{
			width: 100%;
			min-height: 500px;
			height:calc(100vh - 230px);
			height: auto;
		}
    </style>
   <script type="text/javascript">
	$(function(){
		$(document).on("click", ".del", function(){
			// 가상 form 만들어서 전송하기
			let form = $('<form></form>'); // form안에 아래 내용이 들어간다.
			form.attr('action', './delete');  // action 속성 설정
			form.attr("method", "post"); // POST 방식 설정
			
			form.append($("<input>", {type:'hidden', name:"bno", value:$(".bno").val()})); //json 사용
			form.append($("<input>", {type:'hidden', name:"uuid", value:$(".uuid").val()}));
			
			form.appendTo("body");
			form.submit();
		});
		
		
		$(document).on("click", ".edit", function(){
			let bno = $(".bno").val();
			let uuid = $(".uuid").val();
			alert(bno + " / " + uuid);
		});
    
    
       	$(".detail").click(function() { // 한 줄을 클릭한다면
    		let bno = $(this).children("td").eq(0).html(); // 변수선언
    		let title = $(this).children("td").eq(1).text(); 
    		// 제이쿼리 eq() = 선택한 요소의 인덱스 번호에 해당하는 요소를 찾는다. 
    		let name = $(this).children("td").eq(2).html();
   	 		let date = $(this).children("td").eq(3).html();
    		let read = Number($(this).children("td").eq(4).html()) + 1 ; 
    		// number는 괄호()안에 있는 것을 숫자타입으로 바꿔준다.
    		
    		let comment = $(this).children("td").eq(1).children(".bg-secondary").text().length; // 댓글 길이
    		
			if(comment > 0) { // comment가 o보다 크면
				title = title.slice(0, -comment);
				// title" 변수의 문자열에서 마지막에서부터 "comment" 변수의 값만큼 문자를 제거합니다.	 
			}   		
    		
    		// alert(first.text()); // bno값을 뽑아서 modal로 간다
    		// $(".modal-bno").text(bno + "/" + name + "/" + read);
    		
    		$.ajax({ // 통신안되면 아무것도 안되게
    			url:"./detail",
    			type: "post",
    			data: {bno: bno}, 
    			dataType: "json",
    			success:function(data){
    				// alert(data.content);
    				$(".modal-title").text(title); 
    				name = name + '<img class="" src="./img/edit.png"> <img class="del" src="./img/delete.png">';
					name += '<input type="hidden" class="bno" value="'+bno+'">'; // bno 숨기기
					name += '<input type="hidden" class="uuid" value="'+data.uuid+'">'; // uuid 숨기기
    	    		$(".detail-name").html(name);
    	    		$(".detail-date").text(date);
    	    		$(".detail-read").text(data.ip+" / "+read);
    	    		$(".detail-content").html(data.content);
    				$("#exampleModal").modal("show"); 
    				// modal 보여줘 (id는 앞에 #이 붙는다. class는 .)
    	    		
    			},
    			error:function(error){
    				alert("에러가 발생했습니다. 다시 시도하지 마십시오.");
    			}
    		});  
    
    		//$(".modal-title").text($(this).children("td").eq(1).text()); title 값을 뽑아서 modal로 간다
    		
    	});
       	
    });
       	//function detail
       	function detail(bno){
       		// alert(bno + "번을 클릭했습니다.");
       		$.ajax({ // 통신안되면 아무것도 안되게
    			url:"./detail2",
    			type: "post",
    			data: {bno: bno}, 
    			dataType: "json",
    			success:function(data){
    				// alert(data.bno);
    				$(".modal-title").text(data.btitle); 
    				let name = data.m_name + '<img class="" src="./img/edit.png"> <img class="del" src="./img/delete.png">';
					name += '<input type="hidden" class="bno" value="'+data.bno+'">'; // bno 숨기기
					name += '<input type="hidden" class="uuid" value="'+data.uuid+'">'; // uuid 숨기기
    	    		$(".detail-name").html(name);
    	    		$(".detail-date").text(data.bdate);
    	    		$(".detail-read").text(data.bip+" / "+data.blike);
    	    		$(".detail-content").html(data.bcontent);
    				$("#exampleModal").modal("show"); 
    				// modal 보여줘 (id는 앞에 #이 붙는다. class는 .)
    	    		
    			},
    			error:function(error){
    				alert("에러가 발생했습니다. 다시 시도하지 마십시오.");
    			}
       		}); 
       	}
        	
    		// $(".modalOpen").click(function(){$("#exampleModal").modal("show");});    	
    </script>
</head>

<body>
<%@ include file="menu.jsp" %>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container">
                <h1>게시판</h1>
                <table class="table table-dark table-hover table-striped">
                	<thead>
                		<tr class="row">
                			<th class="col-1">번호</th>
                			<th class="col-6">제목</th> <!-- -를 넣어주면 칸을 차지한다 -->
                			<th class="col-2">글쓴이</th>
                			<th class="col-2">날짜</th>
                			<th class="col-1">읽음</th>
                		</tr>
                	</thead>
                	<tbody><c:forEach items="${list }" var="row">
                		<tr class="row" onclick="detail(${row.bno})">            			
                			<td class="col-1">${row.rowNum}</td>
                			<td class="col-6 title">${row.btitle}<c:if test="${row.commentcount ne 0 }">&nbsp;<span class="badge bg-secondary">${row.commentcount }</span></c:if></td>
                			<td class="col-2">${row.m_name }</td>
                			<td class="col-2">${row.bdate }</td>
                			<td class="col-1">${row.blike }</td>
                		</tr></c:forEach>            	
                	</tbody>
                </table>
                <button type="button" class="btn btn-secondary" onclick="location.href='./write'">글쓰기</button>
                <button type="button" id="modal1" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal">모달</button>
                <button type="button" class="modalOpen btn btn-light">모달열기</button>
            </div>
        </header>
        
        <!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      	<div class="detail-detail">
      		<div class="detail-name">이름</div>
      		<div class="detail-date-read">
      			<div class="detail-date">날짜</div>
      			<div class="detail-read">읽음</div>
      		</div>
      		<div class="detail-content">본문내용</div>
      	</div>
        
      </div>
    </div>
  </div>
</div>
	
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