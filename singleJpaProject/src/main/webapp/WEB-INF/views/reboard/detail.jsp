<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/mainTop.jsp" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/formLayout.css'/>" />

<div class="content-wrapper">
	<div class="card">
		<div class="card-body">
		<div class="divForm">
		<div class="firstDiv">
			<span class="sp1">제목</span> <span>${vo.reboardTitle}</span>
		</div>
		<div>
			<span class="sp1">작성자</span> <span>${vo.userid}</span>
		</div>
		<div>
			<span class="sp1">등록일</span> <span>${vo.reboardReg}</span>
		</div>
		<div>
			<span class="sp1">조회수</span> <span>${vo.readcount}</span>
		</div>
		<div>
			<span class="sp1">첨부파일</span> 
			<c:if test="${!empty list }">
				<c:forEach var="vo2" items="${list}">
					<span>
						<img src="<c:url value='/resources/images/file.gif'/>">
						<a href="<c:url value='/download?no=${vo2.fileNo}&filename=${vo2.fileName}'/>">
						 ${vo2.originalFileName }
						</a>
						다운 : ${vo2.downCount }
					</span><br>
				</c:forEach>
			</c:if>
			
		</div>

		<div class="lastDiv">		
			<p class="content">${vo.reboardContent }</p>
		</div>

		<div class="text-center">
		<c:if test="${sessionScope.userid==vo.userid  }">
			<a href
			='<c:url value="/edit?no=${param.reboardNo}"/>'>수정</a> |
		</c:if>
		<c:if test="${sessionScope.userid==vo.userid || sessionScope.auth=='Y'  }">
        	<a id="delA" href
        	='#'>삭제</a> |
		</c:if>
        	<a href
			='<c:url value="/reply?no=${param.reboardNo}"/>'>답변</a> | 
        	<a href='<c:url value="/main"/>'>
        		목록</a>			
		</div>
	</div>
            

		
		</div>
	</div>
</div>

<%@ include file="../inc/mainBottom.jsp" %>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<style type="text/css">


</style>


<script type="text/javascript">
$(function() {
	$("#mainBoard").addClass("active");
	
	$("#delA").click(function() {
		if(confirm("삭제 하시겠습니까?")){
			location.href='<c:url value="/delete?no=${param.reboardNo }"/>';
		}
	});

	
});


</script>