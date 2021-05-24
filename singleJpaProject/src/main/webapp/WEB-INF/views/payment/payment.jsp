<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../inc/mainTop.jsp" %>

<input type="hidden" value="${ vo.email1}@${ vo.email2}" id="useremail"/>
<input type="hidden" value="${ vo.userid}" id="userid"/>
<div class="content-wrapper">
	<div class="card">
		<div class="card-body">
			<div>
				<input readonly="readonly" value="결제시스템 체험으로 만든 것이며 결제는 최소 금액으로 하였습니다."  class="form-control form-control-sm text-center">
				<input type="button" value="결제하기" class="form-control form-control-sm" id="paymentGo"/>
			</div>
		</div>
	</div>
</div>

<%@include file="../inc/mainBottom.jsp" %>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/paymentSystem.js'/>"></script>
<script type="text/javascript">


	
	$(function() {
		
		$("#paymentSystem").addClass("active");
    	

	});
	

	

</script>