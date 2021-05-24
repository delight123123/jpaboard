package com.single.jpaProjct.payment.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.single.jpaProjct.refund.domain.RefundVO;
import com.single.jpaProjct.register.domain.RegisterVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "tbl_payment")
@Entity
public class PaymentVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentNo;
	
	private String ordername;
	
	private String impUid;
	
	private int price;
	
	@CreationTimestamp
	private Timestamp payment_reg;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private RegisterVO register;
	
	@OneToOne(mappedBy = "tbl_refund")
	private RefundVO refundVo;
}
