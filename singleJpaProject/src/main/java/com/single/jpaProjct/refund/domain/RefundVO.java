package com.single.jpaProjct.refund.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "tbl_refund")
@Entity
public class RefundVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long refundNo;
	
	private String refundType;
	
	private int refundPrice;
	
	private Timestamp reportingDate;
	
	private String refundState;
	
	private Timestamp refundDate;
	
	private int paymentNo;
	
	private String refundReason;
}
