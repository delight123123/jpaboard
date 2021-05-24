package com.single.jpaProjct.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.board.common.PaginationInfo;
import com.single.board.common.Utility;
import com.single.board.payment.model.PaymentService;
import com.single.board.payment.model.PaymentVO;
import com.single.board.refund.model.RefundService;
import com.single.board.refund.model.RefundVO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Controller
public class RefundController {

	private final static Logger logger=LoggerFactory.getLogger(RefundController.class);
	
	private IamportClient api=new IamportClient("7198968356913425", "FhiBT3DEezu9djtNYqUiU7PRU2k8Cl4iDmUxpybE8GNlZh9YH3gBa6t48NcL9h4MDYk3xwHFcu0bM99t");
	
	@Autowired
	private RefundService refundService;
	
	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping("/refundList")
	public Object refundList(@ModelAttribute RefundVO refundvo,Model model) {
		logger.info("환불신청 리스트 파라미터 refundvo={}",refundvo);
		
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT);
		pagingInfo.setCurrentPage(refundvo.getCurrentPage());
		
		refundvo.setRecordCountPerPage(Utility.RECORD_COUNT);
		refundvo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		logger.info("셋팅후 paymentVo={}",refundvo);
		
		List<Map<String, Object>> list=refundService.refundSearch(refundvo);
		logger.info("검색결과 list.size()={}",list.size());
		
		int totalRecord=refundService.refundSearchTotal(refundvo);
		logger.info("totalRecord={}",totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		logger.info("pagingInfo={}",pagingInfo);
		
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "payment/refundList";
	}
	
	@ResponseBody
	@RequestMapping("/refundGo")
	public Object refundGo(@RequestParam int refundNo) throws IamportResponseException, IOException {
		logger.info("환불 처리 파라미터 refundNo={}",refundNo);
		
		RefundVO refundVo=refundService.refundSelByno(refundNo);
		
		PaymentVO vo=paymentService.paymentSelByNo(refundVo.getPaymentNo());
		
		String impUid=vo.getImpUid();
		
		logger.info("환불 처리할 impUid={}",impUid);
		
		api.paymentByImpUid(impUid);
		BigDecimal amount=new BigDecimal(refundVo.getRefundPrice());
		CancelData data=new CancelData(impUid, true,amount);
		
		IamportResponse<Payment> response=api.cancelPaymentByImpUid(data);
		
		int code=response.getCode();
		int res=0;
		
		if(code==0) {
			res=refundService.refundUpdate(refundNo);
		}
		
		logger.info("환불 업데이트 결과 res={}",res);
		
		return res;
	}
}
