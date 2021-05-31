package com.single.jpaProjct.payment.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.single.jpaProjct.common.SearchVO;
import com.single.jpaProjct.common.Utility;
import com.single.jpaProjct.register.domain.RegisterVO;

@Service
public class PaymentService {
	
	@Autowired
	PaymentRepository paymentRepository;

	public Page<PaymentVO> paymentListByUserid(RegisterVO vo,SearchVO searchVo){
		String keyword=searchVo.getSearchKeyword();
		
		Pageable paging
		=PageRequest.of(searchVo.getCurrentPage()-1,Utility.BLOCK_SIZE 
				,  Sort.Direction.DESC, "paymentNo");

		Page<PaymentVO> page=Page.empty();
		
		if(!keyword.isEmpty() && keyword!=null) {
			DateTimeFormatter dtf=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime start=LocalDateTime.parse(keyword+" 00:00:00", dtf);
			LocalDateTime end=LocalDateTime.parse(keyword+" 23:59:59", dtf);
			
			page=paymentRepository.findAllByRegisterVoAndPaymentRegBetween(vo,
					Timestamp.valueOf(start), Timestamp.valueOf(end),
					paging);
		}else {
			page=paymentRepository.findAllByRegisterVo(vo, paging);
		}
		
		return page;
	}
	
	public int paymentInsert(PaymentVO paymentVo) {
		int res=0;
		PaymentVO vo=paymentRepository.save(paymentVo);
		if(vo.getPaymentNo()>0) {
			res=1;
		}
		return res;
	}
	
	public PaymentVO paymentSelByimp(String impUid) {
		Optional<PaymentVO> op=paymentRepository.findByImpUid(impUid);
		
		return op.get();
	}
	
	public PaymentVO paymentSelByNo(Long paymentNo) {
		Optional<PaymentVO> op=paymentRepository.findById(paymentNo);
		
		return op.get();
	}
	
}
