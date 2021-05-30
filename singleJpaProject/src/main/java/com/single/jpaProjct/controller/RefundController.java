package com.single.jpaProjct.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.single.jpaProjct.common.PaginationInfo;
import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;
import com.single.jpaProjct.payment.domain.PaymentService;
import com.single.jpaProjct.payment.domain.PaymentVO;
import com.single.jpaProjct.refund.domain.RefundService;
import com.single.jpaProjct.refund.domain.RefundVO;
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
	public Object refundList(@ModelAttribute SearchVO searchVo,Model model) {
		logger.info("환불신청 리스트 파라미터 searchVo={}",searchVo);
		
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORD_COUNT);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		searchVo.setRecordCountPerPage(Utility.RECORD_COUNT);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		logger.info("셋팅후 searchVo={}",searchVo);
		
		Page<RefundVO> page=refundService.refundSearch(searchVo);
		
		List<RefundVO> list=page.stream().collect(Collectors.toList());
		logger.info("검색결과 list.size()={}",list.size());
		
		int totalRecord=(int) page.getTotalElements();
		logger.info("totalRecord={}",totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		logger.info("pagingInfo={}",pagingInfo);
		
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "payment/refundList";
	}
	
	@ResponseBody
	@RequestMapping("/refundGo")
	public Object refundGo(@RequestParam Long refundNo) throws IamportResponseException, IOException {
		logger.info("환불 처리 파라미터 refundNo={}",refundNo);
		
		RefundVO refundVo=refundService.refundSelByno(refundNo);
		
		PaymentVO vo=paymentService.paymentSelByNo(refundVo.getPaymentVo().getPaymentNo());
		
		String impUid=vo.getImpUid();
		
		logger.info("환불 처리할 impUid={}",impUid);
		
		api.paymentByImpUid(impUid);
		BigDecimal amount=new BigDecimal(refundVo.getRefundPrice());
		CancelData data=new CancelData(impUid, true,amount);
		
		IamportResponse<Payment> response=api.cancelPaymentByImpUid(data);
		
		int code=response.getCode();
		int res=0;
		
		if(code==0) {
			refundVo.setRefundState("Y");
			
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String sysdate=sdf.format(date);
			refundVo.setRefundDate(Timestamp.valueOf(sysdate));
			
			res=refundService.refundInsert(refundVo);
		}
		
		logger.info("환불 업데이트 결과 res={}",res);
		
		return res;
	}
}
