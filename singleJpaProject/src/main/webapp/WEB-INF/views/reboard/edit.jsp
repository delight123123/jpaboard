<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/mainTop.jsp" %>

<div class="content-wrapper">
	<div class="card">
		<div class="card-body">
            <div>
            <form name="write" id="fileform" method="post" action="" enctype="multipart/form-data">
				<fieldset>
					<div id="aa">
						<label for="title" >제목</label>
						<input type="text" name="reboardTitle" id="title" class="form-control form-control-fw" value="${vo.reboardTitle }">
					</div>
					<div id="divdiv">
						<label for="content">내용</label><br>
						<textarea id="content" name="reboardContent" rows="12" cols="40" class="form-control form-control-fw">${vo.reboardContent }</textarea>
					</div>
					<div id="lastdiv">
						<button type="button" class="btn btn-gradient-danger btn-rounded btn-fw" id="bfsub">수정</button>
					</div>
				</fieldset>
			</form>
			</div>
		<form id="upfileform" method="post" action="" enctype="multipart/form-data">
			<fieldset>
			<input type="text" id="insertno" name="insertno" value="${vo.reboardNo }">
					<div id="divdiv2">
						<label for="upfile">첨부파일<sup>(파일 새로 선택 시 이전 파일은 모두 삭제)</sup></label>
						<input type="button" name="add" id="add" value="+"><input type="button" name="minus" id="minus" value="-">
						<br>
						<div id="frtfilediv">1. <input type="file" name="upfile1" id="upfile" class="form-control form-control-fw"> </div>
						
						<c:if test="${!empty list }">
							<c:forEach var="vo2" items="${list }">
								<hr>
								<label for="beforeupfile">이미올려진 파일</label>
								<p id="beforeupfile">${vo2.originalFileName }</p>
							</c:forEach>
						</c:if>
						
						
					</div>
			</fieldset>
		</form>
					
					

		
		</div>
	</div>
</div>
<div class="body-overlay" id="overray">
<div id="progressdiv">
	<!-- Ajax Progress Status -->
<div id="viewLoading">
파일 업로드 중입니다.
</div>
</div>
</div>

<%@ include file="../inc/mainBottom.jsp" %>
<script type="text/javascript" src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" 
	href="<c:url value='/resources/css/mainstyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/clear.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/formLayout.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/mystyle.css'/>" />
<style type="text/css">
#overray{
position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        overflow: hidden;
        width: 100%;
        height: 0;
        color: #fff;
        background: rgba(0, 0, 0, 0.6);
        -webkit-transition: .6s ease;
        transition: .6s ease;
        z-index: 1000;
}
#progressdiv{
        position: absolute;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
        text-align: center;
        height: 100%;
}
#fileform{
	width: 100%;
	margin: 0 auto;
}
#fileform #aa,#divdiv,#lastdiv{
	clear: both;
		border-bottom: 1px solid #eee;
		padding: 5px 0;
		margin: 0;
		overflow: auto;
}
#fileform label{
		text-align: right;
		padding: 3px 15px 0 0;
		clear: left;		
		font-weight: bold;
}
#bo_content{
	overflow: hidden;
	width: 10%;
}
#fileform fieldset{
	width: 90%;
		padding: 0;
		margin: 0 auto;
		border: 0;
		border-bottom: 2px solid #e2e2e2;
		}
#title,#upfile{
	width: 90%;
}
#lastdiv{
	text-align: center;
}
#viewLoading{
	background-color: white;
	display: block;
	background-size: cover;
	margin-top: 50%;
	text-align: center;
	color: black;
}
#viewLoading2{
	position: fixed;
        bottom: 0;
        left: 0;
        right: 0;
        overflow: hidden;
        width: 100%;
        height: 0;
        color: #fff;
        background: rgba(0, 0, 0, 0.6);
        -webkit-transition: .6s ease;
        transition: .6s ease;
        z-index: 1000;
}
#divdiv2{
	clear: both;
	border-bottom: 1px solid #eee;
	padding: 5px 0;
	margin: 0 auto;
	overflow: auto;
	width: 90%;
}
</style>


<script type="text/javascript">
function fileup(result) {
	var formdata = new FormData($('#upfileform')[0]);
	alert(result);
	
	$.ajax({
		type: "POST", 
		url:"<c:url value='/fileuplodupdate'/>",
		data: formdata,
		processData: false,
		dataType : "json",
		contentType: false,
		async    : false,
		success:function(){
			alert("업로드 완료");
			location.href="<c:url value='/main'/>"
		},
		error:function(xhr,status,error){
			alert("Error : "+status+", "+error);
		}
	})
};


$(function() {
	var i=1;
	
	$("#minus").hide();
	
	$("#mainBoard").addClass("active");

	$("#bfsub").click(function() {
		$("#overray").css("display","block");
		$("#overray").css("height","100%");


			$.ajax({
				url:"<c:url value='/boardUpdate'/>",
				type: "POST", 
				data: $("form[name=write]").serialize(),
				success:function(res){
					alert("글 수정");
					$("#insertno").val(res);
					fileup(res);
					
				},
				error:function(xhr,status,error){
					alert("Error : "+status+", "+error);
				}
			
			
			
			});
		
	});
	
	
	
	$("#add").click(function() {
			if(i==1){
				$("#frtfilediv").append('<div id="sndfilediv">2. <input type="file" name="upfile1" id="upfile" class="form-control form-control-fw"> </div>');
				$("#minus").show();
				i=2;
			}else if(i==2){
				$("#sndfilediv").append('<div id="thdfilediv">3. <input type="file" name="upfile1" id="upfile" class="form-control form-control-fw"> </div>');
				i=3;
				$("#add").hide();
			}
	});
	
	$("#minus").click(function() {
		if(i==3){
			$("#thdfilediv").remove();
			$("#add").show();
			i=2
		}else if(i==2){
			$("#sndfilediv").remove();
			$("#minus").hide();
			i=1
		}
	});
	
	
	

	
});




</script>