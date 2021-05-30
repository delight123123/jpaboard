package com.single.jpaProjct.refund.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.single.jpaProjct.payment.domain.PaymentVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "tbl_refund")
@DynamicInsert
@DynamicUpdate
@Entity
public class RefundVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long refundNo;
	
	private String refundType;
	
	private int refundPrice;
	
	@CreationTimestamp
	private Timestamp reportingDate;
	
	private String refundState;
	
	private Timestamp refundDate;
	
	@OneToOne
	@JoinColumn(name = "paymentNo")
	private PaymentVO paymentVo;
	
	private String refundReason;
}
