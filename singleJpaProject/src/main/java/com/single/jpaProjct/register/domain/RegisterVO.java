package com.single.jpaProjct.register.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import com.single.jpaProjct.board.domain.ReboardVO;
import com.single.jpaProjct.payment.domain.PaymentVO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Table(name = "TBL_USER")
@ToString(exclude = "reboardList")
@Entity
public class RegisterVO{
	@Id
	private String userid;
	
	private String userpw;
	
	private String email1;
	
	private String email2;
	
	private String salt;
	
	@ColumnDefault("\'N\'")
	private String adminauth;
	
	private Timestamp outDate;
	
	@OneToMany(mappedBy = "registerVo",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private List<ReboardVO> reboardList;
	
	@OneToMany(mappedBy = "register",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private List<PaymentVO> paymentList=new ArrayList<PaymentVO>();
}
