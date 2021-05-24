

$(function() {
	var useremail=$("#useremail").val();
	var userid=$("#userid").val();


	$("#paymentGo").click(function() {
		if (confirm("결제하시겠습니까?")) {
			var IMP = window.IMP; // 생략가능
			IMP.init('imp97242509'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용

			IMP.request_pay({
				pg: 'inicis', // version 1.1.0부터 지원.
				pay_method: 'card',
				merchant_uid: 'merchant_' + new Date().getTime(),
				name: '주문명:결제테스트',
				amount: 151,
				buyer_email: useremail,
				buyer_name: userid,
				buyer_tel: '010-1234-5678',
				m_redirect_url: '/paymentList'
			}, function(rsp) {
				if (rsp.success) {
					// jQuery로 HTTP 요청
					jQuery.ajax({
						url: "/verifyIamport/"+rsp.imp_uid, // 가맹점 서버
						method: "POST"
					}).done(function(data) {
					console.log(data);
						//이 아래에 결제테이블에 insert ajax 함수 넣기
						paymentInser(data.response.impUid,data.response.name,data.response.amount);
						
						
						var msg = "결제가 완료되었습니다.";
						msg += '\n고유ID : ' + data.response.imp_uid;
						msg += '\n상점 거래ID : ' + data.response.merchant_uid;
						msg += '\n결제 금액 : ' + data.response.paid_amount;
						msg += '\n카드 승인번호 : ' + data.response.apply_num;
						alert(msg);
					})
					
				} else {
					var msg = '결제에 실패하였습니다.';
					msg += '에러내용 : ' + rsp.error_msg;
				}

			});



		}
	});
});


function paymentInser(impuid,ordername,price){
	$.ajax({
			url:"/paymentInsert/"+impuid+"/"+ordername+"/"+price,
			type:"post",
			
			success:function(res){
				 if(res==1){
					alert("결제가 완료되었습니다.");
					location.href="/paymentList";
				}else if(res==2){
					
				}else{
					
				} 
				
			},
			error:function(xhr,status,error){
				alert("Error : "+status+", "+error);
			}
		});
};