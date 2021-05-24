<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>싱글프로젝트 - 회원가입</title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="<c:url value='/resources/assets/vendors/mdi/css/materialdesignicons.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/assets/vendors/css/vendor.bundle.base.css'/>">
    <script type="text/javascript" src ="<c:url value = '/resources/js/jquery-3.4.1.min.js'/>"></script>
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <!-- endinject -->
    <!-- Layout styles -->
    <link rel="stylesheet" href="<c:url value='/resources/assets/css/style.css'/>" >
    <!-- End layout styles -->
    <link rel="shortcut icon" href="<c:url value='/resources/assets/images/favicon.png'/>" >
    <script type="text/javascript">
    	var timer;
    	var count=0;
    $(function() {
    	$("#reemail3Chk").css("visibility","hidden");
    	$("#output").hide();
    	$("#email2").change(function(){
    		if($(this).val()=='etc'){
    			$("#email3").val("");
    			$("#email3").css("visibility","visible");
    			$("#email3").focus();				
    		}else{
    			$("#email3").css("visibility","hidden");
    		}
    	});
    	
    	$("#email3Chk").click(function() {
    		var textname=$(this).val();
    		if(textname=="인증하기"){
    			$.ajax({
    				url:"<c:url value='/register/emailcer'/>",
    				type:"post",
    				data:
    					{
    						email1 : $("#email1").val(),
    						email2: $("#email2").val(),
    						email3:$("#email3").val(),
    						cer:$("#emailCh").val()
    					
    					}
    				,
    				success:function(res){
    					 if(res==1){
    						alert("인증완료");
    						//$("#email1").attr("disabled",true);
    						//$("#email2").attr("disabled",true);
    						//$("#email3").attr("disabled",true);
    						$("#emailCh").attr("disabled",true);
    						$("#email3Chk").val("인증완료");
    						$("#email3Chk").attr("disabled",true);
    						$("#reemail3Chk").css("visibility","hidden");
    						stop();
    						$("#output").hide();
    					}else if(res==2){
    						alert("인증실패");    						
    					}else{
    						
    					} 
    					
    				},
    				error:function(xhr,status,error){
    					alert("Error : "+status+", "+error);
    				}
    			});
    			
    			
    		}else if(textname=="인증번호발송"){
    			stop();
	    		ceremailSender();			
    		}else{
    			alert("오류");
    		}
		});
    	
    	$("#reemail3Chk").click(function() {
    		$("#reemail3Chk").css("visibility","hidden");
    		stop();
    		ceremailSender();
    	});
    	
    	
    	$("input[name=userid]").keyup(function() {
			if($(this).val().length>=4){
				$.ajax({
    				url:"<c:url value='/register/idChk'/>",
    				type:"post",
    				data:
    					{
    						useridChk : $("input[name=userid]").val()

    					}
    				,
    				success:function(res){
    					 if(res==1){
    						 $("#idchk").css("visibility","visible");
    						 $("#idchk").css("color","red");
    						 $("#idchk").text("이미 사용중입니다.");
    					}else if(res==0){
    						$("#idchk").css("visibility","visible");
   						 	$("#idchk").css("color","blue");
   						 	$("#idchk").text("사용가능합니다.");
    					}else{
    						
    					} 
    					
    				},
    				error:function(xhr,status,error){
    					alert("Error : "+status+", "+error);
    				}
    			});
				
			}else if($(this).val().length<=0){
				$("#idchk").text("");
				$("#idchk").css("visibility","hidden");
			}else{
				$("#idchk").css("visibility","visible");
				 $("#idchk").css("color","red");
				 $("#idchk").text("길이가 너무 짧습니다.");
			}
		});
    	
    	$("input[name=userpw]").keyup(function() {
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
    	
    	$("input[name=userpw2]").keyup(function() {
    		var pwd=$("input[name=userpw]").val();
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
    	
    	/*$("#sub").click(function() {
    		event.preventDefault();
    		$("form[name=registerForm]").submit(function() {
        		var text1=$("#idchk").text();
        		var text2=$("#invaildpw").text();
        		var text3=$("#invaildpw2").text();
        		
    			if(!$("#chkbox").is(":checked")){
    				alert("약관에 동의하시기 바랍니다.");
    				$("#chkbox").focus();
    				event.preventDefault();
    			}else if($("input[name=userid]").val()<=0){
    				alert("아이디를 입력하시기 바랍니다.");
    				$("input[name=userid]").focus();
    				event.preventDefault();
    			}else if($("input[name=userpw]").val()<=0){
    				alert("비밀번호를 입력하시기 바랍니다.");
    				$("input[name=userpw]").focus();
    				event.preventDefault();
    			}else if($("input[name=userpw2]").val()<=0){
    				alert("비밀번호 확인을 입력하시기 바랍니다.");
    				$("input[name=userpw2]").focus();
    				event.preventDefault();
    			}else if($("#email3Chk").val()!="인증완료"){
    				alert("이메일을 인증 하시기 바랍니다.");
    				$("#email3Chk").focus();
    				event.preventDefault();
    			}else if(text1!="사용가능합니다."){
    				alert("아이디를 확인하시기 바랍니다.");
    				$("input[name=userid]").focus();
    				event.preventDefault();
    			}else if(text2!="사용가능한 비밀번호입니다."){
    				alert("비밀번호를 확인하시기 바랍니다.");
    				$("input[name=userpw]").focus();
    				event.preventDefault();
    			}else if(text3!="비밀번호가 같습니다."){
    				alert("비밀번확인을 확인하시기 바랍니다.");
    				$("input[name=userpw2]").focus();
    				event.preventDefault();
    			}
    		});
		});*/
    	
    	$("form[name=registerForm]").submit(function() {
    		var text1=$("#idchk").text();
    		var text2=$("#invaildpw").text();
    		var text3=$("#invaildpw2").text();
    		//alert("email1="+$("#email1").val());
    		//alert("email2="+$("#email2").val());
			if(!$("#chkbox").is(":checked")){
				alert("약관에 동의하시기 바랍니다.");
				$("#chkbox").focus();
				event.preventDefault();
			}else if($("input[name=userid]").val()<=0){
				alert("아이디를 입력하시기 바랍니다.");
				$("input[name=userid]").focus();
				event.preventDefault();
			}else if($("input[name=userpw]").val()<=0){
				alert("비밀번호를 입력하시기 바랍니다.");
				$("input[name=userpw]").focus();
				event.preventDefault();
			}else if($("input[name=userpw2]").val()<=0){
				alert("비밀번호 확인을 입력하시기 바랍니다.");
				$("input[name=userpw2]").focus();
				event.preventDefault();
			}else if($("#email3Chk").val()!="인증완료"){
				alert("이메일을 인증 하시기 바랍니다.");
				$("#email3Chk").focus();
				event.preventDefault();
			}else if(text1!="사용가능합니다."){
				alert("아이디를 확인하시기 바랍니다.");
				$("input[name=userid]").focus();
				event.preventDefault();
			}else if(text2!="사용가능한 비밀번호입니다."){
				alert("비밀번호를 확인하시기 바랍니다.");
				$("input[name=userpw]").focus();
				event.preventDefault();
			}else if(text3!="비밀번호가 같습니다."){
				alert("비밀번확인을 확인하시기 바랍니다.");
				$("input[name=userpw2]").focus();
				event.preventDefault();
			}
		});
    	
	});
    
/*--------------------------------밑으로 함수들------------------------------------*/    
    
    function start() {
    	stop();
    	$("#output").text("180 초");
		$("#output").show();
		count=180;
		timer=setInterval(function(){
			if(count>=180){
				count--;
			}else if(count==0){
				alert("인증 시간이 만료 되었습니다.");
				stop();
				$("#reemail3Chk").css("visibility","hidden");
				$("#output").css("visibility","hidden");
				$("#email3Chk").val("인증번호발송");
			}else{
				console.log(count+" 초");
				$("#output").text(count+" 초");
				count--;
			}
			
		}, 1000);
		
	};
    
    function stop() {
    	clearInterval(timer);
	};
    
    function ceremailSender() {
    
    	$("#email3Chk").val("메일 전송 중...");
		$("#email3Chk").attr("disabled",true);
		$("#output").text("180 초");
		$("#output").hide();
		
		$.ajax({
			url:"<c:url value='/register/useremail'/>",
			type:"post",
			data:
				{
					email1 : $("#email1").val(),
					email2: $("#email2").val(),
					email3:$("#email3").val()
				
				}
			,
			success:function(res){
				 if(res==1){
					alert("인증번호가 발송되었습니다.");
					$("#email3Chk").val("인증하기");
					$("#email3Chk").attr("disabled",false);
					$("#reemail3Chk").css("visibility","visible");
					start();
					/*$("#output").text("180 초");
					$("#output").show();
					count=180;
					timer=setInterval(function(){
						if(count>=180){
							count--;
						}else if(count==0){
							alert("인증 시간이 만료 되었습니다.");
							clearInterval(timer);
							$("#reemail3Chk").css("visibility","hidden");
							$("#output").css("visibility","hidden");
							$("#email3Chk").val("인증번호발송");
						}else{
							console.log(count+" 초");
							$("#output").text(count+" 초");
							count--;
						}
						
					}, 1000);*/
					
				}else if(res==2){
					
				}else{
					
				} 
				
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
    	
	};
	
	function pwInvaildChk(pwd) {
		var pattern= new RegExp(/^[a-zA-Z0-9]+[!@$^]$/g);
		return pattern.test(pwd);
	};
	
    </script>
  </head>
  <body>
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
          <div class="row flex-grow">
            <div class="col-lg-6 mx-auto">
              <div class="auth-form-light text-left p-5">
              
                <h4>회원가입</h4>

                <form class="pt-3" action="<c:url value='/register'/>" method="post" name="registerForm">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputUsername1" placeholder="아이디" name="userid" style="width: 50%; display: inline;">
                    <span id="idchk" style="visibility: hidden;">안보이지?</span>
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호" name="userpw" style="width: 50%; display: inline;">
                    <span id="invaildpw" style="visibility: hidden;">안보이지?</span>
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호확인" name="userpw2" style="width: 50%; display: inline;">
                    <span id="invaildpw2" style="visibility: hidden;">안보이지?</span>
                  </div>
                  <div class="form-group">	
					<input type="text" name="email1" id = "email1" 
						placeholder="이메일" required="required"  class="form-control form-control-sm" style="width: 20%; display: inline;">
						<span id = "emailType">@</span>
					
					<!-- SELECT-OPTION -->
					<span class="w3ls-text w3ls-name" id = "email2Chk">
						<select id = "email2" name = "email2" class="form-control form-control-sm" style="width: 30%; display: inline;">
							<option value="naver.com">naver.com</option>
							<option value="gmail.com">gmail.com</option>
							<option value="hanmail.net">hanmail.net</option>
							<option value="nate.com">nate.com</option>
							<option value="etc">직접 입력</option>
						</select>
					</span>
					<!-- email3 -->
					<input type="text" name="email3" id = "email3" title="직접입력인 경우 이메일 뒷자리 보여줌" class="form-control form-control-sm"
        				style="visibility:hidden; display: inline; width: 30%;"/><br>
					 <div class="form-check">
        			<input type="text" name="emailCh" id = "emailCh" class="form-control form-control-sm" style="width: 30%; display: inline;"/>
					<input type="button" value="인증번호발송" name="email3Chk" id="email3Chk"  class="btn btn-gradient-dark btn-rounded btn-sm"/>
					<input type="button" value="재전송" name="reemail3Chk" id="reemail3Chk"  class="btn btn-gradient-dark btn-rounded btn-sm" style="visibility: hidden;"/>
					<span id="output" style="margin-left: 20px;" ></span>
					 </div>
                  </div>
                  <div class="mb-4">
                    <div class="form-check"  style="display: inline;">
                      <label class="form-check-label text-muted" style="display: inline;">
                        <input type="checkbox" class="form-check-input" id="chkbox"> 약관 동의 </label>
                    </div>

	                        <input type="button" value="약관 보기" class="btn btn-gradient-dark btn-rounded btn-sm">

                  </div>
                  <div class="mt-3">
                    <button type="submit" class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" id="sub">회원가입</button>
                  </div>
                  <div class="text-center mt-4 font-weight-light"> 이미 아이디가 있나요? <a href="<c:url value='/login'/>" class="text-primary">로그인</a>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- content-wrapper ends -->
      </div>
      <!-- page-body-wrapper ends -->
    </div>
    <!-- container-scroller -->
    <!-- plugins:js -->
    <script src="<c:url value='/resources/assets/vendors/js/vendor.bundle.base.js'/>"></script>
    <!-- endinject -->
    <!-- Plugin js for this page -->
    <!-- End plugin js for this page -->
    <!-- inject:js -->
    <script src="<c:url value='/resources/assets/js/off-canvas.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/hoverable-collapse.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/misc.js'/>" ></script>
    <!-- endinject -->
  </body>
</html>