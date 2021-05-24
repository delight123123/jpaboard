<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/mainTop.jsp" %>

<div class="content-wrapper">
	<div class="card">
		<div class="card-body">

            <form name="write" id="fileform" method="post" action="<c:url value='/reply'/>" enctype="multipart/form-data">
            <input type="text" name="groupno" value="${vo.groupno }">
			<input type="text" name="step" value="${vo.step }">
			<input type="text" name="sortno" value="${vo.sortno }">
			<input type="text" name="reboardNo" value="${vo.reboardNo }">
				<fieldset>
					<div id="aa">
						<label for="title" >제목</label>
						<input type="text" name="reboardTitle" id="title" class="form-control form-control-fw">
					</div>
					<div id="divdiv">
						<label for="content">내용</label><br>
						<textarea id="content" name="reboardContent" rows="12" cols="40" class="form-control form-control-fw"></textarea>
					</div>
					<div id="lastdiv">
						<button type="button" class="btn btn-gradient-danger btn-rounded btn-fw" id="bfsub">작성완료</button>
					</div>
				</fieldset>
			</form>

		
		</div>
	</div>
</div>

<%@ include file="../inc/mainBottom.jsp" %>
<script type="text/javascript" src="<c:url value='/resources/ckeditor/ckeditor.js'/>"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<style type="text/css">
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
#content{
	overflow: hidden;
	width: 90%;
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


</style>


<script type="text/javascript">
$(function() {
	$("#mainBoard").addClass("active");

	$("#bfsub").click(function() {

		$("#fileform").submit();
	});

	
});


</script>