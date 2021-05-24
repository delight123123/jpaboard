<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link rel="stylesheet" 
    href="<c:url value='/resources/assets/vendors/mdi/css/materialdesignicons.min.css'/>">
    <link rel="stylesheet" 
    href="<c:url value='/resources/assets/vendors/css/vendor.bundle.base.css'/>">
<link rel="stylesheet" 
    href="<c:url value='/resources/assets/css/style.css'/>">
    <!-- End layout styles -->
<script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
    <script src="<c:url value='/resources/assets/js/off-canvas.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/hoverable-collapse.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/misc.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/resources/css/jquery-ui.min.css'/>">
<script type="text/javascript">
	$(function(){
		$("input[name=cgPwd]").keyup(function() {
			if($(this).val().length>=6){
				if(pwInvaildChk($(this).val())){
					$("#invaildpw").css("color","blue");
					$("#invaildpw").text("사용가능한 비밀번호입니다.");
					$("#invaildpw").css("visibility","visible");
				}else{
					$("#invaildpw").css("color","red");
					$("#invaildpw").text("소문자,대문자,숫자,(!,@,$,^)만 사용하세요.");
					$("#invaildpw").css("visibility","visible");
				}
			}else if($(this).val().length<=0){
				$("#invaildpw").text("");
				$("#invaildpw").css("visibility","hidden");
			}else{
				$("#invaildpw").css("color","red");
				$("#invaildpw").text("길이가 짧습니다.(6자리 이상)");
				$("#invaildpw").css("visibility","visible");
			}
		});
		
		$("input[name=cgPwd2]").keyup(function() {
    		var pwd=$("input[name=cgPwd]").val();
    		var pwd2=$(this).val();
    		
    		if(pwd2.length>0){
    			if(pwd==pwd2){
        			$("#invaildpw2").css("color","blue");
    				$("#invaildpw2").text("비밀번호가 같습니다.");
    				$("#invaildpw2").css("visibility","visible");
        		}else{
        			$("#invaildpw2").css("color","red");
    				$("#invaildpw2").text("비밀번호가 다릅니다.");
    				$("#invaildpw2").css("visibility","visible");
        		}
    		}else if(pwd2.length==0){
    			$("#invaildpw2").text("");
				$("#invaildpw2").css("visibility","hidden");
    		}
    		
    	});
		
		
		$("input[type=button]").click(function() {
			if($("#currPwd").val()<1){
				alert("현재 비밀번호를 입력하세요.");
				$("#currPwd").focus();
			}else if($("#cgPwd").val().length<1){
				alert("변경할 비밀번호를 입력하세요.");
				$("#cgPwd").focus();
			}else if($("#cgPwd2").val().length<1){
				alert("변경할 비밀번호 확인을 입력하세요.");
				$("#cgPwd2").focus();
			}else if($("#cgPwd").val()!=$("#cgPwd2").val()){
				alert("변경할 비밀번호가 서로 다릅니다.");
				$("#cgPwd").focus();
			}else{
				pwdCg();
			}
		});
	});
	
	
	function pwInvaildChk(pwd) {
		var pattern= new RegExp(/^[a-zA-Z0-9]+[!@$^]$/g);
		return pattern.test(pwd);
	};
	
	function pwdCg(){
		$.ajax({
			url: "<c:url value='/userPwdCg'/>",
			type:"post",
			data: $("form[name=frm]").serialize(),
			success:function(res){
				if(res==1){
					alert("비밀번호가 변경되었습니다. 다시 로그인하시기 바랍니다.");
					window.opener.location="<c:url value='/logout'/>";
					self.close();
				}else if(res==0){
					alert("현재 비밀번호가 틀립니다.");
				}
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
	}
</script>
<style type="text/css">
	body{
		margin: 0 auto;
	}
	#btdiv{
		margin-top: 15px;
	}
	label {
		margin-top: 10px;
	}
</style>
</head>
<body>

<div class="content-wrapper">
<form name="frm">
<input type="hidden" value="${sessionScope.adminUserid}" id="adId" name="adId">
<fieldset>
<legend>비밀번호 변경</legend>
	<div>
		<label for="currPwd">현재 비밀번호</label>
		<input type="password" name="currPwd" id="currPwd" class="form-control">
	</div>
	<div>
		<label for="cgPwd">변경할 비밀번호</label>
		<input type="password" name="cgPwd" id="cgPwd" class="form-control">
		<span id="invaildpw" style="visibility: hidden;">안보이지?</span>
	</div>
	<div>
		<label for="cgPwd2">변경할 비밀번호 확인</label>
		<input type="password" name="cgPwd2" id="cgPwd2" class="form-control">
		<span id="invaildpw2" style="visibility: hidden;">안보이지?</span>
	</div>
</fieldset>
<div class="text-center" id="btdiv">
	<input type="button" value="변경" class="btn btn-success btn-sm">
</div>
</form>
</div>
</body>
</html>