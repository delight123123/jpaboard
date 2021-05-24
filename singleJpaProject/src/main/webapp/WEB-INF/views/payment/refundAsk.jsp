<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>환불 신청</title>
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
        <script src="<c:url value='/resources/assets/vendors/js/vendor.bundle.base.js'/>" ></script>

    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script src="<c:url value='/resources/assets/js/off-canvas.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/hoverable-collapse.js'/>" ></script>
    <script src="<c:url value='/resources/assets/js/misc.js'/>" ></script>
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
	li{
		list-style: none;
	}
</style>
<script type="text/javascript">
	$(function() {
		var max=${vo.price};
		$("#refundPrice").change(function() {
			if($("#refundPrice").val()>max){
				alert("결제하신 금액보다 큽니다.");
				$("#refundPrice").val(max);
			}
		});
		$("#refundPrice").keyup(function() {
			if($("#refundPrice").val()>max){
				alert("결제하신 금액보다 큽니다.");
				$("#refundPrice").val(max);
			}
		});
		
		$("#ask").click(function() {
			if($("#reason").val()==0){
				alert("사유를 작성하여 주시기 바랍니다.");
			}else if($("#reason").val()>0 && $("#reason").val() <5){
				alert("사유를 6글자 이상 적어주시기 바랍니다.");
			}
			else{
				refundInsert();
			}
			
		});
	});
	
	
	function refundInsert() {
		
		$.ajax({
			url:"<c:url value='/refundAskdo'/>",
			type:"post",
			data: $("form[name=frm]").serialize(),
			success:function(res){
				 //alert(res);
				if(res>0){
					alert("환불신청 완료");
					opener.location.reload();
					self.close();
				}
				
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
	};
</script>
</head>
<body>

<div class="content-wrapper">
<form name="frm" method="post">
<input type="hidden" value="${vo.paymentNo}" id="paymentNo" name="paymentNo">
<input type="hidden" value="${vo.price}" id="payPrice" name="payPrice">
<input type="hidden" value="${vo.impUid}" id="imp" name="imp">
<fieldset>
<legend>환불 신청</legend>
	<div>
		<ul>
			<li>
				<label for="reason">사유</label>
				<input type="text" name="reason" id="reason" placeholder="사유를 작성해주세요" class="form-control form-control-sm"/>
			</li>
			<li>
				<label><input type="radio" name="refundSel" id="refundSel" value="all">전액환불</label>
			</li>
			<li>
				<label><input type="radio" name="refundSel" id="refundSel" value="part">부분환불</label>
				<input type="number" value="${vo.price }" max="${vo.price }" min="0" placeholder="환불금액" class="form-control form-control-sm" id="refundPrice" name="refundPrice">
			</li>
		</ul>	
	</div>


</fieldset>
<div class="text-center" id="btdiv">
	<input type="button" value="신청하기" class="btn btn-success btn-sm" id="ask">
</div>
</form>
</div>
</body>
</html>