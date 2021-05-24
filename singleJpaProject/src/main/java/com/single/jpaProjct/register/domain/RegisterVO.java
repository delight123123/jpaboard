package com.single.jpaProjct.register.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.single.jpaProjct.board.domain.ReboardVO;
import com.single.jpaProjct.payment.domain.PaymentVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "tbl_user")
@Entity
public class RegisterVO{
	@Id
	private String userid;
	
	private String userpw;
	
	private String email1;
	
	private String email2;
	
	private String salt;
	
	private String adminauth;
	
	@UpdateTimestamp
	private Timestamp outDate;
	
	@OneToMany(mappedBy = "tbl_user")
	private List<ReboardVO> reboardList=new ArrayList<ReboardVO>();
	
	@OneToMany(mappedBy = "tbl_payment")
	private List<PaymentVO> paymentList=new ArrayList<PaymentVO>();
}
