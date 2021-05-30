package com.single.jpaProjct.board.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Table(name = "upfile_list")
@ToString(exclude = "reboardVo")
@DynamicInsert
@DynamicUpdate
@Entity
public class UpfileListVO {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long fileNo;
	
	private String fileName;
	
	private long filesize;
	
	@ColumnDefault("0")
	private int downCount;
	
	private String originalFileName;
	
	@ManyToOne
	@JoinColumn(name = "reboardNo")
	private ReboardVO reboardVo;
}
