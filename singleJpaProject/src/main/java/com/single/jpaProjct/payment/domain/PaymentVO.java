package com.single.jpaProjct.payment.domain;

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
@Table(name = "tbl_payment")
@Entity
public class PaymentVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentNo;
	
	private String ordername;
	
	private String impUid;
	
	private int price;
	
	private Timestamp payment_reg;
	
	private String userid;
}
