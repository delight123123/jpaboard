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
    <title>싱글프로젝트 - 로그인</title>
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
	$(function(){
		$("#userid").focus();
		
		$("form[name=frm]").submit(function(){
			if($("input[name=userid]").val()==''){
				alert("아이디를 입력하시기 바랍니다.");
				$("input[name=userid]").focus();
				event.preventDefault();
			}else if($("input[name=pwd]").val()==''){
				alert("비밀번호를 입력하시기 바랍니다.");
				$("input[name=pwd]").focus();
				event.preventDefault();
			}
		});
		
		
	});
</script>
  </head>
  <body>
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
          <div class="row flex-grow">
            <div class="col-lg-4 mx-auto">
              <div class="auth-form-light text-left p-5">

                <h4>안녕하세요!</h4>
                <h6 class="font-weight-light">계속 진행하기위해 로그인해주세요.</h6>
                <form class="pt-3" action="<c:url value='/login'/>" method="post" name="frm">
                  <div class="form-group">
                    <input type="text" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="아이디" name="userid" value="${cookie.chk_userid.value }">
                  </div>
                  <div class="form-group">
                    <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="비밀번호" name="userpw">
                  </div>
                  <div class="mt-3">
                    <button class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" type="submit">로그인</button>
                  </div>
                  <div class="my-2 d-flex justify-content-between align-items-center">
                    <div class="form-check">
                      <label class="form-check-label text-muted">
                        <input type="checkbox" class="form-check-input"
                        <c:if test="${!empty cookie.chk_userid }">
							checked="checked"
						</c:if>
                         name="chkSave"> 아이디 저장 </label>
                    </div>
                    <a href="#" class="auth-link text-black">비밀번호를 잊어버리셨어요?</a>
                  </div>

                  <div class="text-center mt-4 font-weight-light"> 아이디가 없나요? <a href="<c:url value='/register'/>" class="text-primary">가입하기</a>
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
    <script src="<c:url value='/resources/assets/vendors/js/vendor.bundle.base.js'/>" ></script>
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