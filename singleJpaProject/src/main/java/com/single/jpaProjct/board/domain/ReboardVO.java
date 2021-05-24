package com.single.jpaProjct.board.domain;

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
@Table(name = "tbl_reboard")
@Entity
public class ReboardVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long reboardNo;
	
	private String reboardTitle;
	
	private String reboardContent;
	
	private Timestamp reboardReg;
	
	private int readcount;
	
	private int groupno;
	
	private int step;
	
	private int sortno;
	
	private String delflag;
	
	private String userid;
	
	private int newImgTerm;
	
	
}
