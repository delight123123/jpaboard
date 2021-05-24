package com.single.jpaProjct.board.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "upfile_list")
@Entity
public class UpfileListVO {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long fileNo;
	
	private String fileName;
	
	private long filesize;
	
	private int downCount;
	
	private String originalFileName;
	
	private int reboardNo;
}
