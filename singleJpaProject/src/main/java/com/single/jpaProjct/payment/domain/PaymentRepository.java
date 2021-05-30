package com.single.jpaProjct.payment.domain;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.single.jpaProjct.register.domain.RegisterVO;

public interface PaymentRepository extends JpaRepository<PaymentVO, Long>{

	public Page<PaymentVO> findAllByRegisterVo(RegisterVO vo, Pageable paging);
	public Page<PaymentVO> findAllByRegisterVoAndPaymentRegBetween(RegisterVO register,
			Timestamp startReg,Timestamp endReg ,Pageable paging);
	public Optional<PaymentVO> findByImpUid(String impUid);
	
}
