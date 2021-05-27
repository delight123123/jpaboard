package com.single.jpaProjct.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReboardRepository extends JpaRepository<ReboardVO, Long>{

	public Page<ReboardVO> findByReboardTitleContaining(String reboardTitle, Pageable paging);
	public Page<ReboardVO> findByReboardContentContaining(String reboardContent, Pageable paging);
	//public Page<ReboardVO> findByRegisterVoContaining(ReboardVO vo, Pageable paging);
	//public Page<ReboardVO> findByUseridContaining(String userid, Pageable paging);
	@Transactional
	@Procedure(procedureName = "delete_reboard")
	public int delProcedure(@Param("p_no") Long no
			,@Param("p_groupno") Long groupno
			,@Param("p_step") Long step);
}
