<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../inc/mainTop.jsp" %>
<form action="<c:url value='/refundList'/>" 
	name="frmPage" method="post">
	<input type="hidden" name="searchCondition" 
		value="reporting_date" id="aa1">
	<input type="hidden" name="searchKeyword" 
		value="${param.searchKeyword}" id="aa2">
	<input type="hidden" name="currentPage" value="${pagingInfo.currentPage }"  id="aa3">

</form>

<div class="content-wrapper">
	<div class="card">
		<div class="card-body">
			<input type="text" id="date" class="form-control form-control-sm"  disabled="disabled" placeholder="옆 버튼을 클릭하여 검색할 날짜를 선택해주세요."/>
			<input type="button" value="검색" class="form-control form-control-sm" id="refundSearch"/>
		</div>
		<div class="card-body">
			<div id="paymentDiv">
				<table class="table text-center" style="width: 100%;">
					<colgroup>
						<col style="width:10%;" />
						<col style="width:35%;" />
						<col style="width:5%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
						<col style="width:10%;" />
					</colgroup>
					<thead>
						<tr>
							<th scope="col">유저명</th>
							<th scope="col">사유</th>
							<th scope="col">환불종류</th>
							<th scope="col">환불금액</th>
							<th scope="col">신청일</th>
							<th scope="col">상태</th>
							<th scope="col">처리일</th>
							<th scope="col">버튼</th>
						</tr>
					</thead>
					<tbody id="payTableBody" style="width: 100%;">
						<c:if test="${empty list }">
							<tr>
								<td colspan="8">환불 내역이 존재하지 않습니다.</td>
							</tr>
						</c:if>
						<c:if test="${!empty list }">
							<c:forEach var="map" items="${list }">
								<tr>
									<td>${map['USERID'] }</td>
									<td>${map['REFUND_REASON'] }</td>
									<td>
										<c:if test="${map['REFUND_TYPE']=='all' }">전액</c:if>
										<c:if test="${map['REFUND_TYPE']=='part' }">부분</c:if>
									</td>
									<td>
										<fmt:formatNumber value="${map['REFUND_PRICE'] }" pattern="#,###" /> 원
									</td>
									<td><fmt:formatDate value="${map['REPORTING_DATE'] }" pattern="yyyy-MM-dd"/> </td>
									<td>${map['REFUND_STATE'] }</td>
									
									<td>
									<c:if test="${!empty map['REFUND_DATE'] }">
										<fmt:formatDate value="${map['REFUND_DATE'] }" pattern="yyyy-MM-dd"/>
									</c:if>
									<c:if test="${empty map['REFUND_DATE'] }">
									-
									</c:if>
									 </td>
									<td>
									<c:if test="${map['REFUND_STATE']=='N' }">
										<input type="button" value="환불" class="refundGo">
										<input type="text" value="${map['REFUND_NO'] }">
									</c:if>
									<c:if test="${map['REFUND_STATE']=='Y' }">
										환불완료
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<div class="divPage text-center">
					<!-- 이전블럭으로 이동 -->
					<c:if test="${pagingInfo.firstPage>1 }">
						<button type="button"
							class='btn btn-social-icon btn-outline-youtube btn-sm'
							onclick="pageFunc(${pagingInfo.firstPage-1})">&lt;&lt;</button>
					</c:if>
					<!-- 페이지 번호 추가 -->

					<c:forEach var="i" begin="${pagingInfo.firstPage }"
						end="${pagingInfo.lastPage }">
						<c:if test="${i==pagingInfo.currentPage }">
							<span class='btn btn-success btn-sm'>${i }</span>
						</c:if>
						<c:if test="${i!=pagingInfo.currentPage }">
							<input type='button' value="${i }" class='btn btn-danger btn-sm'
								onclick="pageFunc(${i})">
						</c:if>
					</c:forEach>
					<!--  페이지 번호 끝 -->

					<!-- 다음블럭으로 이동 -->
					<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">
						<button type="button"
							class="btn btn-social-icon btn-outline-youtube btn-sm"
							onclick="pageFunc(${pagingInfo.lastPage+1})">&gt;&gt;</button>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<%@include file="../inc/mainBottom.jsp" %>
<style type="text/css">
#refundSearch{
	width: 10%;
	display: inline-block;
	margin-left: 1%;
}
#date{
	width: 20%;
	display: inline-block;
}


</style>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.5.js"></script>
<script type="text/javascript">


	
	$(function() {
		
		$("#refundSystem").addClass("active");
    	
		$("#refundSearch").click(function() {
			$("#aa2").val($("#date").val());
			$("form[name=frmPage]").submit();
		});
		
		$(".refundGo").each(function(idx,item) {
			$(this).click(function() {
				var refundNo=$(this).next().val();
				refundGo(refundNo);
			});
		})
		
		
		$("#date").datepicker({ 
			showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다. 
			buttonImage: "<c:url value='/resources/images/calendar.JPG'/>", // 버튼 이미지 
			buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다. 
			changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다. 
			changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다. 
			minDate: '-100y', // 현재날짜로부터 100년이전까지 년을 표시한다. 
			nextText: '다음 달', // next 아이콘의 툴팁. 
			prevText: '이전 달', // prev 아이콘의 툴팁. 
			numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다. 
			stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가. 
			yearRange: 'c-100:c+10', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가. 
			showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다. 
			currentText: '오늘 날짜' , // 오늘 날짜로 이동하는 버튼 패널 
			closeText: '닫기', // 닫기 버튼 패널 
			dateFormat: "yy-mm-dd", // 텍스트 필드에 입력되는 날짜 형식. 
			showAnim: "slide", //애니메이션을 적용한다. 
			showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
			dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], // 요일의 한글 형식. 
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'], // 월의 한글 형식. 
			yearRange: "1900:2021"
			});


	});
	

function refundGo(refundNo) {
		
		$.ajax({
			url:"<c:url value='/refundGo'/>",
			type:"post",
			data: {
				refundNo : refundNo
			},
			success:function(res){
				alert(res);
				if(res>0){
					alert("환불 처리 완료");
					location.reload();
				}
				
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
	};

</script>