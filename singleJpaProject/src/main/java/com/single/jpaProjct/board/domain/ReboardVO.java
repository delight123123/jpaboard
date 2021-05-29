package com.single.jpaProjct.board.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.single.jpaProjct.register.domain.RegisterVO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Table(name = "TBL_REBOARD")
@ToString(exclude = "registerVo")
@Entity
public class ReboardVO{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long reboardNo;
	
	private String reboardTitle;
	
	private String reboardContent;
	
	@CreationTimestamp
	private Timestamp reboardReg;
	
	@ColumnDefault("0")
	private int readcount;
	
	@ColumnDefault("0")
	private Long groupno;
	
	@ColumnDefault("0")
	private Long step;
	
	@ColumnDefault("0")
	private Long sortno;
	
	@ColumnDefault("\'N\'")
	private String delflag;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private RegisterVO registerVo;
	
	@OneToMany(mappedBy = "reboardVo",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private List<UpfileListVO> upfileList=new ArrayList<UpfileListVO>();
	
}
