package com.single.jpaProjct.register.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	private Timestamp outDate;
}
