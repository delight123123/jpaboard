package com.single.jpaProjct.board.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.single.jpaProjct.register.domain.RegisterVO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "TBL_REBOARD")
@Entity
public class ReboardVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long reboardNo;
	
	private String reboardTitle;
	
	private String reboardContent;
	
	@CreationTimestamp
	private Timestamp reboardReg;
	
	private int readcount;
	
	private int groupno;
	
	private int step;
	
	private int sortno;
	
	private String delflag;
	
	@ManyToOne
	@JoinColumn(name="userid")
	private RegisterVO registerVo;
	
	
	@OneToMany(mappedBy = "reboardVo")
	private List<UpfileListVO> upfileList=new ArrayList<UpfileListVO>();
	
}
