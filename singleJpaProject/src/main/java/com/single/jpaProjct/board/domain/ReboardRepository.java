package com.single.jpaProjct.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReboardRepository extends JpaRepository<ReboardVO, Long>{

	public Page<ReboardVO> findByReboardTitleContaining(String reboardTitle, Pageable paging);
	public Page<ReboardVO> findByReboardContentContaining(String reboardContent, Pageable paging);
	public Page<ReboardVO> findByRegisterVoContaining(ReboardVO vo, Pageable paging);
	//public Page<ReboardVO> findByUseridContaining(ReboardVO vo, Pageable paging);
	
}
