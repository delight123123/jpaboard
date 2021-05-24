<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SingleProject - 답변형 게시판</title>
    <!-- plugins:css -->
    <link rel="stylesheet" 
    href="<c:url value='/resources/assets/vendors/mdi/css/materialdesignicons.min.css'/>">
    <link rel="stylesheet" 
    href="<c:url value='/resources/assets/vendors/css/vendor.bundle.base.css'/>">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <!-- endinject -->
    <!-- Layout styles -->
    <link rel="stylesheet" 
    href="<c:url value='/resources/assets/css/style.css'/>">
    <!-- End layout styles -->
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-3.4.1.min.js'/>"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />
  </head>
  <body>
    <div class="container-scroller">
      <!-- partial:../../partials/_navbar.html -->
      <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
          <a class="navbar-brand brand-logo" href="<c:url value='/main'/>"><span class="display-3 text-danger" style="font-weight: bold">Single</span></a>
          <a class="navbar-brand brand-logo-mini" href="<c:url value='/main'/>"><span class="display-3 text-danger" style="font-weight: bold">S</span></a>
          
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-stretch">
          <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
            <span class="mdi mdi-menu"></span>
          </button>

          <ul class="navbar-nav navbar-nav-right">
            <li class="nav-item nav-profile dropdown">
              <a class="nav-link dropdown-toggle" id="profileDropdown" href="#" data-toggle="dropdown" aria-expanded="false">
                <div class="nav-profile-img">
                </div>
                <div class="nav-profile-text">
                  <p class="mb-1 text-black">${sessionScope.userid}</p>
                </div>
              </a>
              <div class="dropdown-menu navbar-dropdown" aria-labelledby="profileDropdown">
                <a class="dropdown-item" href="#" id="pwdcg">
                  <i class="mdi mdi-cached mr-2 text-success"></i> 비밀번호 변경 </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#" id="logout">
                  <i class="mdi mdi-logout mr-2 text-primary"></i> 로그아웃 </a>
              </div>
            </li>
            <li class="nav-item d-none d-lg-block full-screen-link">
              <a class="nav-link">
                <i class="mdi mdi-fullscreen" id="fullscreen-button"></i>
              </a>
            </li>

            <li class="nav-item nav-settings d-none d-lg-block">
              <a class="nav-link" href="#">
                <i class="mdi mdi-format-line-spacing"></i>
              </a>
            </li>
          </ul>
          <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
            <span class="mdi mdi-menu"></span>
          </button>
        </div>
      </nav>
      <!-- partial -->
      <div class="container-fluid page-body-wrapper">
        <!-- partial:../../partials/_sidebar.html -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
          <ul class="nav">
            <li class="nav-item nav-profile text-danger active">
              <a href="#" class="nav-link">
                <div class="nav-profile-image">
                  <i class="mdi mdi-account-circle icon-lg "></i>
                  <span class="login-status online"></span>
                  <!--change to offline or busy as needed-->
                </div>
                <div class="nav-profile-text d-flex flex-column">
                  <span class="font-weight-bold mb-2">${sessionScope.userid}</span>
                  <span class="text-secondary text-small">로그인한 아이디</span>
                </div>
              </a>
            </li>
            <li class="nav-item" id="mainBoard">
              <a class="nav-link z" href="<c:url value='/main'/>">
                <span class="menu-title">답변형 게시판</span>
                <i class="mdi mdi-note-text menu-icon"></i>
              </a>
            </li>

			<!-- 날씨 -->
			<li class="nav-item" id="weather">
              <a class="nav-link z" id="weathergo" onclick="dufqkesp();"  href="<c:url value='/weather'/>">
                <span class="menu-title">날씨</span>
                <i class="mdi mdi-brightness-5 menu-icon"></i>
              </a>
            </li>

			<!-- 결제시스템 -->
			<li class="nav-item" id="paymentSystem">
              <a class="nav-link z" href="<c:url value='/paymemtSystem'/>">
                <span class="menu-title">결제시스템</span>
                <i class="mdi mdi-import menu-icon"></i>
              </a>
            </li>
            
            <!-- 결제 내역 -->
			<li class="nav-item" id="paymentList">
              <a class="nav-link z" href="<c:url value='/paymentList'/>">
                <span class="menu-title">결제 내역</span>
                <i class="mdi mdi-import menu-icon"></i>
              </a>
            </li>
            
			<c:if test="${sessionScope.auth=='Y'}">
	            <!-- 환불 시스템 -->
				<li class="nav-item" id="refundSystem">
	              <a class="nav-link z" href="<c:url value='/refundList'/>">
	                <span class="menu-title">환불 시스템</span>
	                <i class="mdi mdi-import menu-icon"></i>
	              </a>
	            </li>
	            
				<!-- 회원관리 -->
				<li class="nav-item" id="userManagement">
	              <a class="nav-link z" href="<c:url value='/userManagement'/>">
	                <span class="menu-title">회원관리</span>
	                <i class="mdi mdi-import menu-icon"></i>
	              </a>
	            </li>
			</c:if>
			
			<!-- 실시간 채팅 -->
				<li class="nav-item" id="realTimeChat">
	              <a class="nav-link z" href="<c:url value='/realTimeChat'/>">
	                <span class="menu-title">실시간 채팅</span>
	                <i class="mdi mdi-import menu-icon"></i>
	              </a>
	            </li>
			
          </ul>
        </nav>
        <!-- partial -->
        <div class="main-panel">
          <!-- 여기까지 Top -->