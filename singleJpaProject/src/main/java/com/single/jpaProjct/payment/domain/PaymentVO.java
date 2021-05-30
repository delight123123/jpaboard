package com.single.jpaProjct.payment.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.single.jpaProjct.refund.domain.RefundVO;
import com.single.jpaProjct.register.domain.RegisterVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "TBL_PAYMENT")
@DynamicInsert
@DynamicUpdate
@Entity
public class PaymentVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentNo;
	
	private String ordername;
	
	private String impUid;
	
	private int price;
	
	@CreationTimestamp
	private Timestamp paymentReg;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private RegisterVO registerVo;
	
	@OneToOne(mappedBy = "paymentVo",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private RefundVO refundVo;
}
